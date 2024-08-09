package com.ayoolamasha.gopaddi.network.extensions


import com.ayoolamasha.gopaddi.network.mappers.Either
import com.ayoolamasha.gopaddi.network.middleware.NetworkMiddleware
import com.ayoolamasha.gopaddi.network.model.Failure
import com.ayoolamasha.gopaddi.network.model.NetworkConnectionLostSuddenly
import com.ayoolamasha.gopaddi.network.model.ResponseMessage
import com.ayoolamasha.gopaddi.network.model.SSLError
import com.ayoolamasha.gopaddi.network.model.ServiceBodyFailure
import com.ayoolamasha.gopaddi.network.model.TimeOut
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.BufferedSource
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

suspend inline fun <T> call(
    middleWares: List<NetworkMiddleware> = emptyList(),
    ioDispatcher: CoroutineDispatcher,
    adapter: JsonAdapter<ResponseMessage>,
    crossinline retrofitCall: suspend () -> Response<T>
): Either<Failure, T?> {
    return runMiddleWares(middleWares = middleWares)?.toError()
        ?: executeRetrofitCall(ioDispatcher, adapter, retrofitCall)
}

/**
 * Iterate ove all the [NetworkMiddleware] and return true if all of them are valid.
 * @return []
 */
fun runMiddleWares(
    middleWares: List<NetworkMiddleware> = emptyList(),
): Failure? {
    if (middleWares.isEmpty()) return null
    return middleWares.find { !it.isValid() }?.failure
}

suspend inline fun <T> executeRetrofitCall(
    ioDispatcher: CoroutineDispatcher,
    adapter: JsonAdapter<ResponseMessage>,
    crossinline retrofitCall: suspend () -> Response<T>
): Either<Failure, T?> {
    return withContext(ioDispatcher) {
        try {
            val retrofitCallResponse = retrofitCall()
            if (retrofitCallResponse.isSuccessful && retrofitCallResponse.code() in 200..226) {
                Timber.tag("Response Success").d("${retrofitCallResponse.body()}")
                retrofitCallResponse.body().toSuccess()
                return@withContext retrofitCallResponse.body().toSuccess()
            } else if (retrofitCallResponse.code() >= 400) {
                return@withContext Failure.UnexpectedFailure("An error occurred while processing your request.")
                    .toError()
            } else {
                Timber.tag("Response Error").d("${retrofitCallResponse.errorBody()}")
                return@withContext Failure.UnexpectedFailure(getErrorBody(retrofitCallResponse.errorBody()))
                    .toError()
            }
        } catch (e: Exception) {
            return@withContext e.parseException(adapter).toError()
        }
    }
}

fun Throwable.parseException(
    adapter: JsonAdapter<ResponseMessage>
): Failure {
    return when (this) {
        is SocketTimeoutException -> TimeOut
        is SSLException -> NetworkConnectionLostSuddenly
        is SSLHandshakeException -> SSLError
        is IOException -> Failure.UnexpectedFailure(message = this.message)
        is HttpException -> {
            val errorService = adapter.parseError(response()?.errorBody()?.source())
            if (errorService != null) {
                ServiceBodyFailure(
                    internalCode = errorService.code,
                    internalMessage = errorService.message
                )
            } else {
                Failure.UnexpectedFailure(
                    message = "Service ERROR BODY does not match."
                )
            }
        }

        else -> Failure.UnexpectedFailure(
            message = message ?: "Exception not handled caused an Unknown failure"
        )
    }
}

private fun JsonAdapter<ResponseMessage>.parseError(
    json: BufferedSource?
): ResponseMessage? {
    return if (json != null) {
        fromJson(json.toString())
    } else {
        null
    }
}

fun getErrorBody(response: ResponseBody?): String {
    return try {
        val resString = response?.string()
        val errorMessage = JSONObject(resString ?: "").getString("message")

        Timber.tag("Error Messages").d(resString)
        errorMessage
    } catch (e: Exception) {
        e.message ?: "Please try again"
    }
}
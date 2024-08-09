package com.ayoolamasha.gopaddi.network.model

import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

sealed class Failure {

    /**
     * Extend this class in order to provide your own
     * custom failure.
     */
    open class CustomFailure() : Failure()

    data class UnexpectedFailure(
        val message: String?
    ) : Failure()

}

fun Failure.getErrorMessage(): String? {
    return when (this) {
        is Failure.UnexpectedFailure -> this.message
        is Failure.CustomFailure -> this.getCustomErrorMessage()
    }
}

fun Failure.CustomFailure.getCustomErrorMessage(): String? {
    return when (this) {
        is NetworkMiddlewareFailure -> this.middleWareExceptionMessage
        is TimeOut -> SocketTimeoutException().localizedMessage
        is NetworkConnectionLostSuddenly -> SSLException("Connection Lost").localizedMessage
        is ServiceBodyFailure -> this.internalMessage
        else -> "Exception not handled caused an Unknown failure"
    }
}


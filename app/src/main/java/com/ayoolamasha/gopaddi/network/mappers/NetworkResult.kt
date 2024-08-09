package com.firstcentralcreditbureau.fcbmobile.network.mappers

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Exception(val t: Throwable) : NetworkResult<Nothing>()

    class Loading<T>(data: T? = null) : NetworkResult<T>()

}

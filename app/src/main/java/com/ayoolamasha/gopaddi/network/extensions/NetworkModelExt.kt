package com.ayoolamasha.gopaddi.network.extensions

import com.ayoolamasha.gopaddi.network.mappers.Either


fun <R> R.toSuccess(): Either.Success<R> {
    return Either.Success(this)
}

fun <R> R.toAny(): Any {
    return when (this) {
        is Either.Success<*> -> Either.Success(this)
        else -> Either.Error(this)
    }

}

fun <L> L.toError(): Either.Error<L> {
    return Either.Error(this)
}
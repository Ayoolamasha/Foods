package com.ayoolamasha.gopaddi.network.middleware

import com.ayoolamasha.gopaddi.network.model.NetworkMiddlewareFailure


abstract class NetworkMiddleware {
    abstract val failure: NetworkMiddlewareFailure

    abstract fun isValid(): Boolean
}
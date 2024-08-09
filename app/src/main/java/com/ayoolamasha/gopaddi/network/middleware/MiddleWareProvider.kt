package com.ayoolamasha.gopaddi.network.middleware

interface MiddlewareProvider {
    fun getAll(): List<NetworkMiddleware>
}

class MiddlewareProviderImpl constructor(
    private val middlewareList: List<NetworkMiddleware> = listOf()
) : MiddlewareProvider {

    class Builder(
        private val middlewareList: MutableList<NetworkMiddleware> = mutableListOf()
    ) {

        fun add(middleware: NetworkMiddleware) = apply {
            this.middlewareList.add(middleware)
        }

        fun build() = MiddlewareProviderImpl(
            middlewareList = middlewareList
        )
    }


    override fun getAll(): List<NetworkMiddleware> = middlewareList
}
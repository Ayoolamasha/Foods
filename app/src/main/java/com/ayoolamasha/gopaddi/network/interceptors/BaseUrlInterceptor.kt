package com.ayoolamasha.gopaddi.network.interceptors

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalHttpUrl: HttpUrl = request.url

        val newBaseUrl = when {
            originalHttpUrl.encodedPath.contains("trips") -> {
                "https://cadfc2781c00439dc5a3.free.beeceptor.com/api/"
            }
            originalHttpUrl.encodedPath.contains("flights") -> {
                "https://cac3a3692fd1035bffae.free.beeceptor.com/api/"
            }
            originalHttpUrl.encodedPath.contains("hotels") -> {
                "https://cae9b325270699b877ff.free.beeceptor.com/api/hotels/"
            }
            originalHttpUrl.encodedPath.contains("activities") -> {
                "https://ca08c17a63bace5c368c.free.beeceptor.com/api/activities/"
            }
            else -> {
                "https://cadfc2781c00439dc5a3.free.beeceptor.com/api/"
            }
        }

        val newHttpUrl = originalHttpUrl.newBuilder()
            .scheme(newBaseUrl.toHttpUrlOrNull()!!.scheme)
            .host(newBaseUrl.toHttpUrlOrNull()!!.host)
            .port(newBaseUrl.toHttpUrlOrNull()!!.port)
            .build()

        request = request.newBuilder().url(newHttpUrl).build()
        return chain.proceed(request)
    }
}
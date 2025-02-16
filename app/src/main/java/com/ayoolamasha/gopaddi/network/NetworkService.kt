package com.ayoolamasha.gopaddi.network


import com.ayoolamasha.gopaddi.network.interceptors.BaseUrlInterceptor
import com.ayoolamasha.gopaddi.network.interceptors.SocketTimeOutInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NetworkService @Inject constructor(private val moshi: Moshi) {

    fun createRetrofit(url: String, isDebug: Boolean): Retrofit {
        val client: OkHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return Retrofit.Builder()
            .baseUrl(url)
            .delegatingCallFactory { client }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            //.addInterceptor(BaseUrlInterceptor())
            .addInterceptor(SocketTimeOutInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            //.addInterceptor(UnsuccessfulCallInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BASIC
            HttpLoggingInterceptor.Level.BODY
            HttpLoggingInterceptor.Level.HEADERS
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }


    @Suppress("NOTHING_TO_INLINE")
    private inline fun Retrofit.Builder.delegatingCallFactory(
        delegate: dagger.Lazy<OkHttpClient>
    ): Retrofit.Builder = callFactory {
        delegate.get().newCall(it)
    }

    private inline fun Retrofit.Builder.callFactory(
        crossinline body: (Request) -> Call
    ): Retrofit.Builder = callFactory { request -> body(request) }
}
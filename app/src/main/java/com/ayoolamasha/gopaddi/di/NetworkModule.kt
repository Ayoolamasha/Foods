package com.ayoolamasha.gopaddi.di



import com.ayoolamasha.gopaddi.apiService.FoodApiService
import com.ayoolamasha.gopaddi.network.NetworkService
import com.ayoolamasha.gopaddi.network.middleware.ConnectivityMiddleware
import com.ayoolamasha.gopaddi.network.middleware.MiddlewareProvider
import com.ayoolamasha.gopaddi.network.middleware.MiddlewareProviderImpl
import com.ayoolamasha.gopaddi.network.model.ResponseMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://assessment.vgtechdemo.com/api/"

    @[Provides Singleton]
    fun provideRetrofit(networkFactory: NetworkService): Retrofit =
        networkFactory.createRetrofit(
            url = BASE_URL,
            isDebug = false
        )

    @[Provides Singleton]
    fun provideResponseJsonAdapter(moshi: Moshi): JsonAdapter<ResponseMessage> {
        val typeT = Types.newParameterizedType(ResponseMessage::class.java, String::class.java)
        return moshi.adapter(typeT)
    }


    @[Provides Singleton]
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun bindMiddlewareProvider(
        connectivityMiddlewareImpl:
        ConnectivityMiddleware
    ): MiddlewareProvider =
        MiddlewareProviderImpl.Builder()
            .add(middleware = connectivityMiddlewareImpl)
            .build()


    @Singleton
    @Provides
    fun provideTripApiService(retrofit: Retrofit): FoodApiService{
        return retrofit.create(FoodApiService::class.java)
    }
}
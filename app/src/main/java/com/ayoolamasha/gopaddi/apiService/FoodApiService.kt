package com.ayoolamasha.gopaddi.apiService


import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetCategoryTagsResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFoodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface FoodApiService {
    @Multipart
    @POST("foods")
    suspend fun createFood(
        @PartMap requestBodyMap: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part images: List<MultipartBody.Part>
    ): Response<CreateFoodResponse>

    @GET("foods")
    suspend fun getFoods(): Response<GetFoodResponse>

    @GET("categories")
    suspend fun getFoodCategories(): Response<GetCategoryTagsResponse>

    @GET("tags")
    suspend fun getFoodTags(): Response<GetCategoryTagsResponse>
}
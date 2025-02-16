package com.ayoolamasha.gopaddi.apiService

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetCategoryTagsResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFoodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface FoodApiServiceHelper {

    suspend fun createFood(
        requestBodyMap: Map<String, @JvmSuppressWildcards RequestBody>,
        images: List<MultipartBody.Part>
    ): Response<CreateFoodResponse>

    suspend fun getFoodCategories(): Response<GetCategoryTagsResponse>

    suspend fun getFoodTags(): Response<GetCategoryTagsResponse>

    suspend fun getFoods(): Response<GetFoodResponse>
}
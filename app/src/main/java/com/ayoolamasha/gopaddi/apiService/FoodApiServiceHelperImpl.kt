package com.ayoolamasha.gopaddi.apiService


import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetCategoryTagsResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFoodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodApiServiceHelperImpl @Inject constructor(private val foodApiService: FoodApiService): FoodApiServiceHelper{
    override suspend fun createFood(
        requestBodyMap: Map<String, RequestBody>,
        images: List<MultipartBody.Part>
    ): Response<CreateFoodResponse> {
        return foodApiService.createFood(
            requestBodyMap = requestBodyMap,
            images = images
        )
    }

    override suspend fun getFoodCategories(): Response<GetCategoryTagsResponse> {
        return foodApiService.getFoodCategories()
    }

    override suspend fun getFoodTags(): Response<GetCategoryTagsResponse> {
        return foodApiService.getFoodTags()
    }

    override suspend fun getFoods(): Response<GetFoodResponse> {
        return foodApiService.getFoods()
    }

}
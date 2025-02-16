package com.ayoolamasha.gopaddi.featureTrips.data.mapper

import com.ayoolamasha.gopaddi.featureTrips.data.model.Categories
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.FoodImages
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetCategoryTagsData
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetCategoryTagsResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFoodResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFoodResponseData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CategoriesUI
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FoodImagesUI
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


//fun CreateFoodData.toMultipart(): Map<String, Any> {
//    val requestBodyMap = mutableMapOf<String, RequestBody>()
//
//    requestBodyMap["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())
//    requestBodyMap["description"] = description.toRequestBody("text/plain".toMediaTypeOrNull())
//    requestBodyMap["category_id"] = categoryId.toRequestBody("text/plain".toMediaTypeOrNull())
//    requestBodyMap["calories"] = calories.toRequestBody("text/plain".toMediaTypeOrNull())
//
//    tags.forEachIndexed { index, tag ->
//        requestBodyMap["tags[$index]"] = tag.toRequestBody("text/plain".toMediaTypeOrNull())
//    }
//
//    val imageParts = imageFiles.mapIndexed { index, file ->
//        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
//        MultipartBody.Part.createFormData("images[$index]", file.name, requestFile)
//    }
//
//    return mapOf(
//        "requestBodyMap" to requestBodyMap,
//        "imageParts" to imageParts
//    )
//}

fun CreateFoodData.toMultipart(): Pair<Map<String, RequestBody>, List<MultipartBody.Part>> {
    val requestBodyMap = mutableMapOf<String, RequestBody>()

    requestBodyMap["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["description"] = description.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["category_id"] = categoryId.toRequestBody("text/plain".toMediaTypeOrNull())
    requestBodyMap["calories"] = calories.toRequestBody("text/plain".toMediaTypeOrNull())

    tags.forEachIndexed { index, tag ->
        requestBodyMap["tags[$index]"] = tag.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    val imageParts = imageFiles.mapIndexed { index, file ->
        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("images[$index]", file.name, requestFile)
    }

    return Pair(requestBodyMap, imageParts)
}


fun CreateFoodResponse.createFoodMapper(): CreateFoodUiData {
    return CreateFoodUiData(
        message = message,
    )

}

fun GetFoodResponse.getFoodMapper(): List<GetFoodUiData> {
    return data?.map { it.toGetFoodUi() } ?: emptyList()

}

fun GetFoodResponseData.toGetFoodUi(): GetFoodUiData{
    return GetFoodUiData(
        id = id,
        name = name,
        desc = desc,
        calories = calories,
        foodTags = foodTags,
        foodImages = foodImages?.map { it.toFoodImageUI() },
        categoryName = category?.name,
        categoryDesc = category?.desc,
    )
}

fun FoodImages.toFoodImageUI(): FoodImagesUI{
    return FoodImagesUI(
        imageUrl = imageUrl
    )
}

fun Categories.toCategory(): CategoriesUI{
    return CategoriesUI(
        name = name,
        desc = desc
    )
}


fun GetCategoryTagsResponse.getCategoryTagMapper(): List<GetCategoryTagsUIData> {
    return categoryTagsData?.map { it.toTagName() } ?: emptyList()
}

private fun GetCategoryTagsData.toTagName(): GetCategoryTagsUIData {
    return GetCategoryTagsUIData(
        id = id,
        name = name
    )
}


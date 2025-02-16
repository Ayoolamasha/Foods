package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetFoodResponse(
    @Json(name = "status")
    val success: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: List<GetFoodResponseData>?,

)

@JsonClass(generateAdapter = true)
data class GetFoodResponseData(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val desc: String?,
    @Json(name = "calories")
    val calories: String?,
    @Json(name = "foodTags")
    val foodTags: List<String>?,
    @Json(name = "foodImages")
    val foodImages: List<FoodImages>?,
    @Json(name = "category")
    val category: Categories?,

)

@JsonClass(generateAdapter = true)
data class FoodImages(
    @Json(name = "image_url")
    val imageUrl: String?,
)

@JsonClass(generateAdapter = true)
data class Categories(
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val desc: String?,
)


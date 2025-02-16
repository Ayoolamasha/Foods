package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.File

data class CreateFoodData(
    val name: String,
    val description: String,
    val categoryId: String,
    val calories: String,
    val tags: List<String>,
    val imageFiles: List<File>
)

@JsonClass(generateAdapter = true)
data class CreateFoodResponse(
    @Json(name = "status")
    val success: String,
    @Json(name = "message")
    val message: String
)




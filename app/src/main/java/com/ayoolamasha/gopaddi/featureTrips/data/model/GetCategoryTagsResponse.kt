package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCategoryTagsResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val categoryTagsData: List<GetCategoryTagsData>?,
)

@JsonClass(generateAdapter = true)
data class GetCategoryTagsData(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
)

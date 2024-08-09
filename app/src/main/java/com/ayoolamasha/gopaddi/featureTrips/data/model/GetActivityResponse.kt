package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetActivityResponse(
    @Json(name = "activity_name")
    val activityName: String,
    @Json(name = "activity_details")
    val activityDetails: String,
    @Json(name = "activity_location")
    val activityLocation: String,
    @Json(name = "activity_rating")
    val activityRating: String,
    @Json(name = "activity_duration")
    val activityDuration: String,
    @Json(name = "activity_time")
    val activityTime: String,
    @Json(name = "acivity_price")
    val activityPrice: String,
    @Json(name = "id")
    val id: String,
)

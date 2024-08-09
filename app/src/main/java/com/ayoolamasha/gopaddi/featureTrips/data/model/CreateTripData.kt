package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateTripData(
    @Json(name = "city")
    val city: String,
    @Json(name = "start_date")
    val startDate: String,
    @Json(name = "end_date")
    val endDate: String,
    @Json(name = "trip_name")
    val tripName: String,
    @Json(name = "trip_style")
    val tripStyle: String,
    @Json(name = "trip_desc")
    val tripDesc: String,
)

data class CreateTripDataResponse(
    @Json(name = "id")
    val id: String,
)

package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetFlightResponse(
    @Json(name = "airline")
    val airline: String,
    @Json(name = "airline_code")
    val airlineCode: String,
    @Json(name = "trip_duration")
    val tripDuration: String,
    @Json(name = "take_off_location")
    val takeOffLocation: String,
    @Json(name = "take_off_time")
    val takeOffTime: String,
    @Json(name = "touch_down_time")
    val touchDownTime: String,
    @Json(name = "touch_down_location")
    val touchDownLocation: String,
    @Json(name = "flight_date")
    val flightDate: String,
    @Json(name = "flight_price")
    val flightPrice: String,
    @Json(name = "id")
    val id: String,
)

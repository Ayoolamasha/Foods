package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetHotelResponse(
    @Json(name = "hotel_name")
    val hotelName: String,
    @Json(name = "hotel_address")
    val hotelAddress: String,
    @Json(name = "hotel_rating")
    val hotelRating: String,
    @Json(name = "hotel_bed_size")
    val hotelBedSize: String,
    @Json(name = "hotel_check_in_time")
    val hotelCheckInTime: String,
    @Json(name = "hotel_check_out_time")
    val hotelCheckOutTime: String,
    @Json(name = "hotel_price")
    val hotelPrice: String,
    val id: String,
)

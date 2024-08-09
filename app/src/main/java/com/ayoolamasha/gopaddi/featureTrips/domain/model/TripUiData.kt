package com.ayoolamasha.gopaddi.featureTrips.domain.model




data class CreateTripUiData(
    val id: String,
)

data class TripsUIData(
    val city: String,
    val startDate: String,
    val endDate: String,
    val tripName: String,
    val tripStyle: String,
    val tripDesc: String,
    val id: String,
)

data class FlightUiData(
    val airline: String,
    val airlineCode: String,
    val tripDuration: String,
    val takeOffLocation: String,
    val takeOffTime: String,
    val touchDownTime: String,
    val touchDownLocation: String,
    val flightDate: String,
    val flightPrice: String,
    val id: String,
)

data class HotelUiData(
    val hotelName: String,
    val hotelAddress: String,
    val hotelRating: String,
    val hotelBedSize: String,
    val hotelCheckInTime: String,
    val hotelCheckOutTime: String,
    val hotelPrice: String,
    val id: String,
)

data class ActivityUiData(
    val activityName: String,
    val activityDetails: String,
    val activityLocation: String,
    val activityRating: String,
    val activityDuration: String,
    val activityTime: String,
    val activityPrice: String,
    val id: String,
)

data class CountriesUIData(
    val name: String?,
    val flag: String?,
    val iso2: String?,
    val iso3: String?,
)

data class CreateTripSharedData(
    val tripCity: String,
    val tripStateData: String,
    val tripEndDate: String,
)

data class TripDetailsUiData(
    val city: String,
    val startDate: String,
    val endDate: String,
    val tripName: String,
    val tripStyle: String,
    val tripDesc: String,
    val id: String,
)

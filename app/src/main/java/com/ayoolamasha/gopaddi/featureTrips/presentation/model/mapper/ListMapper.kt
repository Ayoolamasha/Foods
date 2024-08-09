package com.ayoolamasha.gopaddi.featureTrips.presentation.model.mapper

import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData


fun List<FlightUiData>.flightUiListMapper(): FlightUiData? {
    return this.firstOrNull()?.let { flightData ->
        FlightUiData(
            airline = flightData.airline,
            airlineCode = flightData.airlineCode,
            tripDuration = flightData.tripDuration,
            takeOffLocation = flightData.takeOffLocation,
            takeOffTime = flightData.takeOffTime,
            touchDownTime = flightData.touchDownTime,
            touchDownLocation = flightData.touchDownLocation,
            flightDate = flightData.flightDate,
            flightPrice = flightData.flightPrice,
            id = flightData.id
        )
    }
}
fun List<HotelUiData>.hotelUiListMapper(): HotelUiData? {
    return this.firstOrNull()?.let { hotelData ->
        HotelUiData(
            hotelName = hotelData.hotelName,
            hotelAddress = hotelData.hotelAddress,
            hotelRating = hotelData.hotelRating,
            hotelBedSize = hotelData.hotelBedSize,
            hotelCheckInTime = hotelData.hotelCheckInTime,
            hotelCheckOutTime = hotelData.hotelCheckOutTime,
            hotelPrice = hotelData.hotelPrice,
            id = hotelData.id
        )
    }
}

fun List<ActivityUiData>.activityUiListMapper(): ActivityUiData? {
    return this.firstOrNull()?.let { activityData ->
        ActivityUiData(
            activityName = activityData.activityName,
            activityDetails = activityData.activityDetails,
            activityLocation = activityData.activityLocation,
            activityRating = activityData.activityRating,
            activityDuration = activityData.activityDuration,
            activityTime = activityData.activityTime,
            activityPrice = activityData.activityPrice,
            id = activityData.id
        )
    }
}

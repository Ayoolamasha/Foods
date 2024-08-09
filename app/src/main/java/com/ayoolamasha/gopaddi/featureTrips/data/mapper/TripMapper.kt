package com.ayoolamasha.gopaddi.featureTrips.data.mapper

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripDataResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetActivityResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFlightResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetHotelResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetTripsResponse
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData

fun CreateTripDataResponse.createTripMapper(): CreateTripUiData {
    return CreateTripUiData(
        id = id
    )
}

fun GetTripsResponse.getTripsMapper(): TripsUIData {
    return TripsUIData(
        city = city,
        startDate = startDate,
        endDate = endDate,
        tripName = tripName,
        tripStyle = tripStyle,
        tripDesc = tripDesc,
        id = id,
    )

}

fun GetFlightResponse.getFlightsMapper(): FlightUiData {
    return FlightUiData(
        airline,
        airlineCode,
        tripDuration,
        takeOffLocation,
        takeOffTime,
        touchDownTime,
        touchDownLocation,
        flightDate,
        flightPrice,
        id
    )
}

fun GetHotelResponse.getHotelsMapper(): HotelUiData {
    return HotelUiData(
        hotelName,
        hotelAddress,
        hotelRating,
        hotelBedSize,
        hotelCheckInTime,
        hotelCheckOutTime,
        hotelPrice,
        id
    )
}

fun GetActivityResponse.getActivityMapper(): ActivityUiData {
    return ActivityUiData(
        activityName,
        activityDetails,
        activityLocation,
        activityRating,
        activityDuration,
        activityTime,
        activityPrice,
        id
    )
}
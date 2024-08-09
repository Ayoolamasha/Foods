package com.ayoolamasha.gopaddi.featureTrips.presentation.state

import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData

data class CreateTripState(
    val isLoading: Boolean = false,
    val createTripUiData: CreateTripUiData? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class TripState(
    val isLoading: Boolean = false,
    val tripsUIData: List<TripsUIData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class FlightState(
    val isLoading: Boolean = false,
    val flightUiData: List<FlightUiData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class HotelState(
    val isLoading: Boolean = false,
    val hotelUiData: List<HotelUiData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class ActivityState(
    val isLoading: Boolean = false,
    val activityUiData: List<ActivityUiData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)


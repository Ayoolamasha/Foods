package com.ayoolamasha.gopaddi.apiService

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripDataResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetActivityResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFlightResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetHotelResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetTripsResponse
import retrofit2.Response
import retrofit2.http.Body

interface TripApiServiceHelper {

    suspend fun createTrip(@Body createTripData: CreateTripData): Response<CreateTripDataResponse>

    suspend fun getTrips(): Response<List<GetTripsResponse>>

    suspend fun getFlights(): Response<List<GetFlightResponse>>

    suspend fun getHotels(): Response<List<GetHotelResponse>>

    suspend fun getActivities(): Response<List<GetActivityResponse>>
}
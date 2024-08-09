package com.ayoolamasha.gopaddi.apiService

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripDataResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetActivityResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFlightResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetHotelResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetTripsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TripApiService {
    @POST("trips")
    suspend fun createTrip(@Body createTripData: CreateTripData): Response<CreateTripDataResponse>

    @GET("trips")
    suspend fun getTrips(): Response<List<GetTripsResponse>>

    @GET("flights")
    suspend fun getFlights(): Response<List<GetFlightResponse>>

    @GET("hotels")
    suspend fun getHotels(): Response<List<GetHotelResponse>>

    @GET("activities")
    suspend fun getActivities(): Response<List<GetActivityResponse>>
}
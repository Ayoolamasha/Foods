package com.ayoolamasha.gopaddi.apiService

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripDataResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetActivityResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetFlightResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetHotelResponse
import com.ayoolamasha.gopaddi.featureTrips.data.model.GetTripsResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripApiServiceHelperImpl @Inject constructor(private val tripApiService: TripApiService): TripApiServiceHelper{
    override suspend fun createTrip(createTripData: CreateTripData): Response<CreateTripDataResponse> {
        return tripApiService.createTrip(createTripData = createTripData)
    }

    override suspend fun getTrips(): Response<List<GetTripsResponse>> {
        return tripApiService.getTrips()
    }

    override suspend fun getFlights(): Response<List<GetFlightResponse>> {
        return tripApiService.getFlights()
    }

    override suspend fun getHotels(): Response<List<GetHotelResponse>> {
        return tripApiService.getHotels()
    }

    override suspend fun getActivities(): Response<List<GetActivityResponse>> {
        return tripApiService.getActivities()
    }
}
package com.ayoolamasha.gopaddi.featureTrips.data.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.ayoolamasha.gopaddi.network.mappers.Either
import com.ayoolamasha.gopaddi.network.model.Failure

interface TripRepositoryHelper {

    suspend fun createTrip(createTripData: CreateTripData): Either<Failure, CreateTripUiData>

    suspend fun getTrips(): Either<Failure, List<TripsUIData>>

    suspend fun getFlights(): Either<Failure, List<FlightUiData>>

    suspend fun getActivity(): Either<Failure, List<ActivityUiData>>

    suspend fun getHotels(): Either<Failure, List<HotelUiData>>
}
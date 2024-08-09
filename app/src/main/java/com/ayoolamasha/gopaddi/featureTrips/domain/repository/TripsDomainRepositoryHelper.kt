package com.ayoolamasha.gopaddi.featureTrips.domain.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData

import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.flow.Flow

interface TripsDomainRepositoryHelper {

fun createTrip(createTripData: CreateTripData): Flow<NetworkResult<CreateTripUiData>>
fun getTrip (): Flow<NetworkResult<List<TripsUIData>>>

fun getFlights(): Flow<NetworkResult<List<FlightUiData>>>

fun getHotel(): Flow<NetworkResult<List<HotelUiData>>>

fun getActivities(): Flow<NetworkResult<List<ActivityUiData>>>
}
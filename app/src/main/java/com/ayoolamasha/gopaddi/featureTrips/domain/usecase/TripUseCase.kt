package com.ayoolamasha.gopaddi.featureTrips.domain.usecase

import com.ayoolamasha.gopaddi.di.IoDispatcher
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.TripsDomainRepositoryHelper
import com.ayoolamasha.gopaddi.network.BaseUseCase
import com.ayoolamasha.gopaddi.network.requireParams
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MakeCreateTripCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: TripsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<CreateTripData, NetworkResult<CreateTripUiData>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: CreateTripData?): Flow<NetworkResult<CreateTripUiData>> {
        requireParams(params)
        return domainRepositoryHelper.createTrip(createTripData = params)
    }
}

@Singleton
class MakeGetTripsCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: TripsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<TripsUIData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<TripsUIData>>> {
        return domainRepositoryHelper.getTrip()
    }
}

@Singleton
class MakeGetFlightListCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: TripsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<FlightUiData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<FlightUiData>>> {
        return domainRepositoryHelper.getFlights()
    }
}

@Singleton
class MakeGetHotelsCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: TripsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<HotelUiData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<HotelUiData>>> {
        return domainRepositoryHelper.getHotel()
    }
}

@Singleton
class MakeGetActivityCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: TripsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<ActivityUiData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<ActivityUiData>>> {
        return domainRepositoryHelper.getActivities()
    }
}

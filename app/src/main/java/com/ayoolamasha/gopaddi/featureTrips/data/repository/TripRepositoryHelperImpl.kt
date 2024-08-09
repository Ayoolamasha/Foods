package com.ayoolamasha.gopaddi.featureTrips.data.repository

import com.ayoolamasha.gopaddi.apiService.TripApiServiceHelper
import com.ayoolamasha.gopaddi.di.IoDispatcher
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.createTripMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getActivityMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getFlightsMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getHotelsMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getTripsMapper
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData

import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.ayoolamasha.gopaddi.network.extensions.call
import com.ayoolamasha.gopaddi.network.mappers.Either
import com.ayoolamasha.gopaddi.network.middleware.MiddlewareProvider
import com.ayoolamasha.gopaddi.network.model.Failure
import com.ayoolamasha.gopaddi.network.model.ResponseMessage
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryHelperImpl @Inject constructor(
    private val middlewareProvider: MiddlewareProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val errorAdapter: JsonAdapter<ResponseMessage>,
    private val tripApiServiceHelper: TripApiServiceHelper
):TripRepositoryHelper{
    override suspend fun createTrip(createTripData: CreateTripData): Either<Failure, CreateTripUiData> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                tripApiServiceHelper.createTrip(
                    createTripData = createTripData
                )
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.let { response ->
                   response.createTripMapper()

                }
            }

        } as Either<Failure, CreateTripUiData>
    }

    override suspend fun getTrips(): Either<Failure, List<TripsUIData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                tripApiServiceHelper.getTrips()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.let { response ->
                    response.map { it.getTripsMapper() }

                }
            }

        } as Either<Failure, List<TripsUIData>>

    }

    override suspend fun getFlights(): Either<Failure, List<FlightUiData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                tripApiServiceHelper.getFlights()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.let { response ->
                    response.map { it.getFlightsMapper() }

                }
            }

        } as Either<Failure, List<FlightUiData>>
    }

    override suspend fun getActivity(): Either<Failure, List<ActivityUiData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                tripApiServiceHelper.getActivities()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.let { response ->
                    response.map { it.getActivityMapper() }

                }
            }

        } as Either<Failure, List<ActivityUiData>>
    }

    override suspend fun getHotels(): Either<Failure, List<HotelUiData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                tripApiServiceHelper.getHotels()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.let { response ->
                    response.map { it.getHotelsMapper() }

                }
            }

        } as Either<Failure, List<HotelUiData>>
    }
}
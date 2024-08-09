package com.ayoolamasha.gopaddi.featureTrips.domain.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.data.repository.TripRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.domain.model.ActivityUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.FlightUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.HotelUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.ayoolamasha.gopaddi.network.model.getErrorMessage
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripsDomainRepositoryImpl @Inject constructor(
    private val tripRepositoryHelper: TripRepositoryHelper
): TripsDomainRepositoryHelper {
    override fun createTrip(createTripData: CreateTripData): Flow<NetworkResult<CreateTripUiData>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                tripRepositoryHelper.createTrip(createTripData = createTripData)

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }
    }

    override fun getTrip(): Flow<NetworkResult<List<TripsUIData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                tripRepositoryHelper.getTrips()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }
    }

    override fun getFlights(): Flow<NetworkResult<List<FlightUiData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                tripRepositoryHelper.getFlights()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }
    }

    override fun getHotel(): Flow<NetworkResult<List<HotelUiData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                tripRepositoryHelper.getHotels()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }
    }

    override fun getActivities(): Flow<NetworkResult<List<ActivityUiData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                tripRepositoryHelper.getActivity()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }
    }

}
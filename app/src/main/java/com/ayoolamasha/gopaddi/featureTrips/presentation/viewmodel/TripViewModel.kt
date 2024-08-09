package com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayoolamasha.gopaddi.common.JsonFileReader
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateTripData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CountriesUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeCreateTripCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetActivityCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetFlightListCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetHotelsCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetTripsCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.ActivityState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CreateTripState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.FlightState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.HotelState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.TripState
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(
    private val makeCreateTripCallsUseCase: MakeCreateTripCallsUseCase,
    private val makeGetTripsCallsUseCase: MakeGetTripsCallsUseCase,
    private val makeGetFlightListCallsUseCase: MakeGetFlightListCallsUseCase,
    private val makeGetHotelsCallsUseCase: MakeGetHotelsCallsUseCase,
    private val makeGetActivityCallsUseCase: MakeGetActivityCallsUseCase,
    private val jsonFileReader: JsonFileReader
): ViewModel(){

    private val _postCreateTripState = MutableStateFlow(CreateTripState())
    val createTripState get() = _postCreateTripState

    private val _getGetTripsState = MutableStateFlow(TripState())
    val getTripsState get() = _getGetTripsState

    private val _getFlightState = MutableStateFlow(FlightState())
    val getFlightState get() = _getFlightState

    private val _getHotelState = MutableStateFlow(HotelState())
    val getHotelState get() = _getHotelState

    private val _getActivityState = MutableStateFlow(ActivityState())
    val getActivityState get() = _getActivityState

    fun makeCreateTripCall(
        city: String,
        startDate: String,
        endDate: String,
        tripName: String,
        tripStyle: String,
        tripDesc: String
    ) {
        val request = CreateTripData(
            city, startDate, endDate, tripName, tripStyle, tripDesc
        )
        makeCreateTripCallsUseCase(request).onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _postCreateTripState.update {
                            CreateTripState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _postCreateTripState.update {
                            CreateTripState(createTripUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _postCreateTripState.update {
                            CreateTripState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _postCreateTripState.update {
                        CreateTripState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }

    fun makeGetTripCall() {
        makeGetTripsCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getGetTripsState.update {
                            TripState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getGetTripsState.update {
                            TripState(tripsUIData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getGetTripsState.update {
                            TripState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getGetTripsState.update {
                        TripState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }
    fun makeGetFlightCall() {
        makeGetFlightListCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getFlightState.update {
                            FlightState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getFlightState.update {
                            FlightState(flightUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getFlightState.update {
                            FlightState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getFlightState.update {
                        FlightState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }

    fun makeGetHotelCall() {
        makeGetHotelsCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getHotelState.update {
                            HotelState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getHotelState.update {
                            HotelState(hotelUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getHotelState.update {
                            HotelState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getHotelState.update {
                        HotelState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }
    fun makeGetActivityCall() {
        makeGetActivityCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getActivityState.update {
                            ActivityState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getActivityState.update {
                            ActivityState(activityUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getActivityState.update {
                            ActivityState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getActivityState.update {
                        ActivityState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }

    fun getCountriesJsonData(): List<CountriesUIData> {
        return jsonFileReader.readCountriesJsonArray()
    }
}
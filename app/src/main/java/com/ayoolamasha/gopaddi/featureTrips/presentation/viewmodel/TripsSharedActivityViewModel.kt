package com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripSharedData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripDetailsUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TripsSharedActivityViewModel @Inject constructor() : ViewModel(){

    private val _selectedCountry = MutableStateFlow("")
    val selectedCountry get() = _selectedCountry

    var createTripSharedData: CreateTripSharedData? = null
    var tripDetailsUiData: TripDetailsUiData? = null


    fun getSelectedCountry(name: String){
        _selectedCountry.value = name
    }


}
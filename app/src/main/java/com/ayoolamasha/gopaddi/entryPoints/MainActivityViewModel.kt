package com.ayoolamasha.gopaddi.entryPoints

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _isLoadingSplashScreen = MutableStateFlow(false)
    val isLoadingSplashScreen = _isLoadingSplashScreen

    init {
        viewModelScope.launch {
            delay(5000L)
            _isLoadingSplashScreen.value = true
        }
    }
}
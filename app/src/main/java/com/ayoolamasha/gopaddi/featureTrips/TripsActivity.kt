package com.ayoolamasha.gopaddi.featureTrips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayoolamasha.gopaddi.databinding.ActivityTripNavHostHolderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTripNavHostHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
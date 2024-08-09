package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ayoolamasha.gopaddi.common.showProgressPopUpDialog
import com.ayoolamasha.gopaddi.common.showSnackBar
import com.ayoolamasha.gopaddi.common.stopProgressLoading
import com.ayoolamasha.gopaddi.databinding.FragmentTripDetailsBinding
import com.ayoolamasha.gopaddi.featureTrips.presentation.model.mapper.activityUiListMapper
import com.ayoolamasha.gopaddi.featureTrips.presentation.model.mapper.flightUiListMapper
import com.ayoolamasha.gopaddi.featureTrips.presentation.model.mapper.hotelUiListMapper
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.ActivityState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.FlightState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.HotelState
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripsSharedActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripsDetailsFragment : Fragment(){
    private lateinit var binding: FragmentTripDetailsBinding
    private val tripsViewModel: TripViewModel by viewModels()
    private val tripsSharedActivityViewModel: TripsSharedActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTripDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tripDetails = tripsSharedActivityViewModel.tripDetailsUiData
        /**
         * GET FLIGHT STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            tripsViewModel.getFlightState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getFlightState(it) }
        }

        /**
         * GET ACTIVITY STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            tripsViewModel.getActivityState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getActivityState(it) }
        }

        /**
         * GET HOTEL STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            tripsViewModel.getHotelState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getHotelState(it) }
        }

        binding.activityInclude.addActivity.setOnClickListener { tripsViewModel.makeGetActivityCall() }
        binding.hotelInclude.addHotel.setOnClickListener { tripsViewModel.makeGetHotelCall() }
        binding.flightInclude.addFlight.setOnClickListener { tripsViewModel.makeGetFlightCall() }

        binding.activityInclude.removeActivity.setOnClickListener {
            binding.activityInclude.emptyActivity.visibility = View.VISIBLE
            binding.activityInclude.viewActivity.visibility = View.GONE
        }

        binding.hotelInclude.removeHotel.setOnClickListener {
            binding.hotelInclude.emptyHotel.visibility = View.VISIBLE
            binding.hotelInclude.viewHotel.visibility = View.GONE
        }

        binding.flightInclude.removeFlight.setOnClickListener {
            binding.flightInclude.noFlight.visibility = View.VISIBLE
            binding.flightInclude.viewFlight.visibility = View.GONE
        }

        binding.backArrow.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun getFlightState(flightState: FlightState) {
        if (flightState.isLoading) {
            showProgressPopUpDialog()
        } else if (flightState.isError) {
            stopProgressLoading()
            showSnackBar(content = flightState.error.toString())
        } else if (flightState.isSuccess) {
            stopProgressLoading()
            binding.flightInclude.noFlight.visibility = View.GONE
            binding.flightInclude.viewFlight.visibility = View.VISIBLE
            binding.flightInclude.flightData = flightState.flightUiData?.flightUiListMapper()
        }
    }

    private fun getHotelState(hotelState: HotelState) {
        if (hotelState.isLoading) {
            showProgressPopUpDialog()
        } else if (hotelState.isError) {
            stopProgressLoading()
            showSnackBar(content = hotelState.error.toString())
        } else if (hotelState.isSuccess) {
            stopProgressLoading()
            binding.hotelInclude.emptyHotel.visibility = View.GONE
            binding.hotelInclude.viewHotel.visibility = View.VISIBLE
            binding.hotelInclude.hotelData = hotelState.hotelUiData?.hotelUiListMapper()
        }
    }

    private fun getActivityState(activityState: ActivityState) {
        if (activityState.isLoading) {
            showProgressPopUpDialog()
        } else if (activityState.isError) {
            stopProgressLoading()
            showSnackBar(content = activityState.error.toString())
        } else if (activityState.isSuccess) {
            stopProgressLoading()
            binding.activityInclude.emptyActivity.visibility = View.GONE
            binding.activityInclude.viewActivity.visibility = View.VISIBLE
            binding.activityInclude.activityUiData = activityState.activityUiData?.activityUiListMapper()
        }
    }


}
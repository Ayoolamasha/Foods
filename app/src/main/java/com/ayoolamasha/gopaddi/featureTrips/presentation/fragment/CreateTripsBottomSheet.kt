package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.common.showProgressPopUpDialog
import com.ayoolamasha.gopaddi.common.showSnackBar
import com.ayoolamasha.gopaddi.common.stopProgressLoading
import com.ayoolamasha.gopaddi.databinding.BottomSheetCreateTripBinding
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CreateTripState
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripsSharedActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTripsBottomSheet : BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetCreateTripBinding
    private val tripsViewModel: TripViewModel by viewModels()
    private val tripsSharedActivityViewModel: TripsSharedActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = BottomSheetCreateTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * GET TRIPS STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            tripsViewModel.createTripState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { createTripsState(it) }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.trip_style_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.travelStyleSpinner.adapter = adapter
        }

        binding.createTrip.setOnClickListener{ validate() }
    }

    private fun validate(){
        val tripName = binding.tripName.text.toString().trim()
        val tripDesc = binding.tripDesc.text.toString().trim()
        val tripStyle = binding.travelStyleSpinner.selectedItem.toString().trim()

        if (tripName.isEmpty()){
            showSnackBar(content = "Trip name cannot be empty")
        }else{
            tripsViewModel.makeCreateTripCall(
                city = tripsSharedActivityViewModel.createTripSharedData?.tripCity.toString(),
                startDate = tripsSharedActivityViewModel.createTripSharedData?.tripStateData.toString(),
                endDate = tripsSharedActivityViewModel.createTripSharedData?.tripEndDate.toString(),
                tripName = tripName,
                tripStyle = tripStyle,
                tripDesc = tripDesc
            )
        }


    }

    private fun createTripsState(createTripState: CreateTripState) {
        if (createTripState.isLoading) {
            showProgressPopUpDialog()
        } else if (createTripState.isError) {
            stopProgressLoading()
            showSnackBar(content = createTripState.error.toString())
        } else if (createTripState.isSuccess) {
            stopProgressLoading()
            showSnackBar("Trip Created Successfully")

           val action = CreateTripsBottomSheetDirections.actionCreateTripsBottomSheetToTripsDashboardFragment()
            findNavController().navigate(action)

        }
    }

}
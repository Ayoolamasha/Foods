package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayoolamasha.gopaddi.common.showMaterialDataPickerDialog
import com.ayoolamasha.gopaddi.common.showSnackBar
import com.ayoolamasha.gopaddi.databinding.FragmentTripDashboardBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateTripSharedData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripDetailsUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.TripsAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.TripState
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripsSharedActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripsDashboardFragment : Fragment(){
    private lateinit var binding: FragmentTripDashboardBinding
    private val tripsViewModel: TripViewModel by viewModels()
    private val tripsSharedActivityViewModel: TripsSharedActivityViewModel by activityViewModels()
    private lateinit var tripsAdapter: TripsAdapter
    private var startDate = ""
    private var endDate = ""
    private var city = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTripDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripsViewModel.makeGetTripCall()

        /**
         * GET TRIPS STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            tripsViewModel.getTripsState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getTripsState(it) }
        }

        binding.createTripInclude.createTrip.setOnClickListener{
           navigateToBottomSheet()
        }

        binding.createTripInclude.selectCity.setOnClickListener{
            openCountriesDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            tripsSharedActivityViewModel.selectedCountry
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it.isBlank().not()){
                        binding.createTripInclude.selecedCity.text = it
                        city = it
                    }
                     }
        }


        binding.createTripInclude.selectedStartDate.setOnClickListener {
            showMaterialDataPickerDialog { chosenDate ->
                binding.createTripInclude.selectedStartDate.text = chosenDate
                startDate = chosenDate
            }
        }

        binding.createTripInclude.selectedEndDate.setOnClickListener {
            showMaterialDataPickerDialog { chosenDate ->
                binding.createTripInclude.selectedEndDate.text = chosenDate
                endDate = chosenDate
            }
        }

    }


    private fun getTripsState(tripState: TripState) {
        if (tripState.isLoading) {
            binding.showProgress.isVisible = true
        } else if (tripState.isError) {
            binding.showProgress.isVisible = false
        } else if (tripState.isSuccess) {
            binding.showProgress.isVisible = false
            binding.tripRecycler.isVisible = true
            initRecycler()
            tripsAdapter.submitList(tripState.tripsUIData)

        }
    }

    private fun initRecycler(){
        tripsAdapter = TripsAdapter{
            navigateToTripDetails(it)
        }
        binding.tripRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = tripsAdapter
        }
    }


    private fun openCalendarDialog(){
        val datePicker = DatePickerFragment()
        datePicker.show(requireActivity().supportFragmentManager, tag)
    }

    private fun openCountriesDialog(){
        val countryListFragment = CountryListFragment()
        countryListFragment.show(requireActivity().supportFragmentManager, tag)
    }

    private fun navigateToBottomSheet(){
        if (city.isBlank() || startDate.isBlank() || endDate.isBlank()){
            showSnackBar(content = "All input field must be filled")
        }else{
            val createTripSharedData = CreateTripSharedData(
                tripCity = city,
                tripStateData = startDate,
                tripEndDate = endDate
            )
            tripsSharedActivityViewModel.createTripSharedData = createTripSharedData
            val action = TripsDashboardFragmentDirections.actionTripsDashboardFragmentToCreateTripsBottomSheet()
            findNavController().navigate(action)
        }


    }

    private fun navigateToTripDetails(tripsUIData: TripsUIData){
        val tripDetails = TripDetailsUiData(
            city = tripsUIData.city,
            startDate = tripsUIData.startDate,
            endDate = tripsUIData.endDate,
            tripName = tripsUIData.tripName,
            tripStyle = tripsUIData.tripStyle,
            tripDesc = tripsUIData.tripDesc,
            id = tripsUIData.id
        )

        tripsSharedActivityViewModel.tripDetailsUiData = tripDetails

        val action = TripsDashboardFragmentDirections.actionTripsDashboardFragmentToTripsDetailsFragment()
        findNavController().navigate(action)
    }
}
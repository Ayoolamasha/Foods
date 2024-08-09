package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.databinding.FragmentCountryListBinding
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.CountriesAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.TripsSharedActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListFragment : DialogFragment(){
    private lateinit var binding: FragmentCountryListBinding
    private val tripsViewModel: TripViewModel by viewModels()
    private val tripsSharedActivityViewModel: TripsSharedActivityViewModel by activityViewModels()
    private lateinit var countriesAdapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Calendar_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeDialog.setOnClickListener{dismiss()}

        initRecycler()
        countriesAdapter.setData(tripsViewModel.getCountriesJsonData())

        binding.enterSearchName.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                countriesAdapter.filter.filter(p0)

                return true
            }

        })
    }

    private fun initRecycler(){
        countriesAdapter = CountriesAdapter{
            tripsSharedActivityViewModel.getSelectedCountry(name = it.name.toString())
            dismiss()
        }
        binding.countryRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = countriesAdapter
        }
    }
}
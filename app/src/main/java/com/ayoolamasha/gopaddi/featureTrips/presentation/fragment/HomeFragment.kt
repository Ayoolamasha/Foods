package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.content.Context
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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.common.showProgressPopUpDialog
import com.ayoolamasha.gopaddi.common.showSnackBar
import com.ayoolamasha.gopaddi.common.stopProgressLoading
import com.ayoolamasha.gopaddi.databinding.FragmentHomeBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.FoodsAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.HomeCategoryAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CategoryTagsState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.FoodsState
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.FoodsViewModel
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.FoodsSharedActivityViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeBinding
    private val foodsViewModel: FoodsViewModel by viewModels()
    private lateinit var foodsAdapter: FoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodsViewModel.makeGetFoodCall()
        foodsViewModel.makeGetCategoryCall()

        /**
         * GET TRIPS STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            foodsViewModel.getFoodsState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getTripsState(it) }
        }

        /**
         * GET TRIPS STATE
         */
        viewLifecycleOwner.lifecycleScope.launch {
            foodsViewModel.getCategoryState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { getCategoryState(it) }
        }




    }

    private fun getTripsState(foodsState: FoodsState) {
        if (foodsState.isLoading) {
            showProgressPopUpDialog()
        } else if (foodsState.isError) {
            stopProgressLoading()
            showSnackBar(foodsState.error.toString())
        } else if (foodsState.isSuccess) {
            stopProgressLoading()
            binding.allFoodRecycler.isVisible = true
            initRecycler()
            foodsAdapter.submitList(foodsState.foodsUIData)

        }
    }

    private fun getCategoryState(categoryTagsState: CategoryTagsState) {
        if (categoryTagsState.isLoading) {
        } else if (categoryTagsState.isError) {
        } else if (categoryTagsState.isSuccess) {
            initCategoryRecyclerView(tags = categoryTagsState.getCategoryTagsUiData)

        }
    }

    private fun initRecycler(){
        foodsAdapter = FoodsAdapter{}
        binding.allFoodRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = foodsAdapter
        }
    }

    private fun initCategoryRecyclerView(tags: List<GetCategoryTagsUIData>?) {
       val homeCategoryAdapter = HomeCategoryAdapter {  }
        homeCategoryAdapter.submitList(tags)

        binding.varitiesChip.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = homeCategoryAdapter
            isNestedScrollingEnabled = false
        }
    }

}
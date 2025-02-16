package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayoolamasha.gopaddi.databinding.BottomSheetCategoryTagBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.presentation.adapter.CategoryTagAdapter
import com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel.FoodsSharedActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryTagsBottomSheet(private val tagName : List<GetCategoryTagsUIData>) : BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetCategoryTagBinding
    private val foodsSharedActivityViewModel: FoodsSharedActivityViewModel by activityViewModels()
    private lateinit var categoryTagAdapter: CategoryTagAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = BottomSheetCategoryTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()




    }

    private fun initRecycler(){
        val categoryAdapter = CategoryTagAdapter {
            foodsSharedActivityViewModel.getSelectedTag(it.name.toString(), id = it.id ?: 1)
            dismiss()
        }

        binding.bottomSheetRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = categoryAdapter
        }
        categoryAdapter.submitList(tagName)
    }




}
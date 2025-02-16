package com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodsSharedActivityViewModel @Inject constructor() : ViewModel(){

    private val _selectedTag = MutableStateFlow(GetCategoryData(id = 1, name = ""))
    val selectedTag get() = _selectedTag



    fun getSelectedTag(name: String, id: Int){
        val categoryData = GetCategoryData(id = id, name= name)
        _selectedTag.value = categoryData
    }


}
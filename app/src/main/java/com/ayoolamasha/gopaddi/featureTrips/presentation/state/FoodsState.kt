package com.ayoolamasha.gopaddi.featureTrips.presentation.state


import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData

data class CreateFoodState(
    val isLoading: Boolean = false,
    val createFoodUiData: CreateFoodUiData? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class FoodsState(
    val isLoading: Boolean = false,
    val foodsUIData: List<GetFoodUiData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)

data class CategoryTagsState(
    val isLoading: Boolean = false,
    val getCategoryTagsUiData: List<GetCategoryTagsUIData>? = null,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = ""
)


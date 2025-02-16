package com.ayoolamasha.gopaddi.featureTrips.domain.model

data class CreateFoodUiData(
    val message: String,
)

data class GetCategoryTagsUIData(
    val id: Int?,
    val name: String?,
)

data class GetCategoryData(
    val id: Int?,
    val name: String?,
)


data class DetailsSharedData(
    val tripCity: String,
    val tripStateData: String,
    val tripEndDate: String,
)

data class GetFoodUiData(
    val id: Int?,
    val name: String?,
    val desc: String?,
    val calories: String?,
    val foodTags: List<String>?,
    val foodImages: List<FoodImagesUI>?,
    val categoryName: String?,
    val categoryDesc: String?,
)

data class FoodImagesUI(
    val imageUrl: String?,
)
data class CategoriesUI(
    val name: String?,
    val desc: String?,)

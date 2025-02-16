package com.ayoolamasha.gopaddi.featureTrips.domain.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.flow.Flow

interface FoodsDomainRepositoryHelper {

fun createFood(createFoodData: CreateFoodData): Flow<NetworkResult<CreateFoodUiData>>

fun getFoods (): Flow<NetworkResult<List<GetFoodUiData>>>

fun getCategory(): Flow<NetworkResult<List<GetCategoryTagsUIData>>>

fun getTags(): Flow<NetworkResult<List<GetCategoryTagsUIData>>>

}
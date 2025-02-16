package com.ayoolamasha.gopaddi.featureTrips.data.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.ayoolamasha.gopaddi.network.mappers.Either
import com.ayoolamasha.gopaddi.network.model.Failure

interface FoodsRepositoryHelper {

    suspend fun createFoods(createFoodData: CreateFoodData): Either<Failure, CreateFoodUiData>

    suspend fun getFoods(): Either<Failure, List<GetFoodUiData>>

    suspend fun getCategory(): Either<Failure, List<GetCategoryTagsUIData>>

    suspend fun getTags(): Either<Failure, List<GetCategoryTagsUIData>>

}
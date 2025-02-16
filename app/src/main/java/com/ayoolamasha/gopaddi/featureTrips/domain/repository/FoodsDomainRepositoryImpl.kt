package com.ayoolamasha.gopaddi.featureTrips.domain.repository

import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.data.repository.FoodsRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.ayoolamasha.gopaddi.network.model.getErrorMessage
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodsDomainRepositoryImpl @Inject constructor(
    private val foodsRepositoryHelper: FoodsRepositoryHelper
): FoodsDomainRepositoryHelper {
    override fun createFood(createFoodData: CreateFoodData): Flow<NetworkResult<CreateFoodUiData>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                foodsRepositoryHelper.createFoods(createFoodData = createFoodData)

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }.catch { error ->
            emit(NetworkResult.Error(error.message))
        }
    }

    override fun getFoods(): Flow<NetworkResult<List<GetFoodUiData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                foodsRepositoryHelper.getFoods()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }.catch { error ->
            emit(NetworkResult.Error(error.message))
        }
    }

    override fun getCategory(): Flow<NetworkResult<List<GetCategoryTagsUIData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                foodsRepositoryHelper.getCategory()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }.catch { error ->
            emit(NetworkResult.Error(error.message))
        }
    }

    override fun getTags(): Flow<NetworkResult<List<GetCategoryTagsUIData>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response =
                foodsRepositoryHelper.getTags()

            if (response.isSuccess) {
                val uiCase = response.getSuccessOrNull()
                if (uiCase != null) {
                    emit(NetworkResult.Success(uiCase))
                }
            } else {
                val failure = response.getFailureOrNull()
                if (failure != null) {
                    emit(NetworkResult.Error(failure.getErrorMessage()))
                }
            }
        }.catch { error ->
            emit(NetworkResult.Error(error.message))
        }
    }


}
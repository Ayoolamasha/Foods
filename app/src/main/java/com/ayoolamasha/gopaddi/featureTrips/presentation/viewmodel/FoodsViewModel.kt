package com.ayoolamasha.gopaddi.featureTrips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeCreateFoodCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetCategoryCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetFoodCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.domain.usecase.MakeGetTagsCallsUseCase
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CategoryTagsState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.CreateFoodState
import com.ayoolamasha.gopaddi.featureTrips.presentation.state.FoodsState
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val makeCreateFoodCallsUseCase: MakeCreateFoodCallsUseCase,
    private val makeGetFoodCallsUseCase: MakeGetFoodCallsUseCase,
    private val makeGetTagsCallsUseCase: MakeGetTagsCallsUseCase,
    private val makeGetCategoryCallsUseCase: MakeGetCategoryCallsUseCase,
): ViewModel(){

    private val _postCreateFoodState = MutableStateFlow(CreateFoodState())
    val createFoodState get() = _postCreateFoodState

    private val _getGetFoodsState = MutableStateFlow(FoodsState())
    val getFoodsState get() = _getGetFoodsState

    private val _getCategoryState = MutableStateFlow(CategoryTagsState())
    val getCategoryState get() = _getCategoryState

    private val _getTagState = MutableStateFlow(CategoryTagsState())
    val getTagState get() = _getTagState

    fun makeCreateFoodCall(
        name: String,
        desc: String,
        categoryId: String,
        calories: String,
        tags: List<String>,
        imageFiles: List<File>,
    ) {
        val request = CreateFoodData(
            name = name,
            description = desc,
            categoryId = categoryId,
            calories = calories,
            tags = tags,
            imageFiles = imageFiles
        )
        makeCreateFoodCallsUseCase(request).onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _postCreateFoodState.update {
                            CreateFoodState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _postCreateFoodState.update {
                            CreateFoodState(createFoodUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _postCreateFoodState.update {
                            CreateFoodState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _postCreateFoodState.update {
                        CreateFoodState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }

    fun makeGetFoodCall() {
        makeGetFoodCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getGetFoodsState.update {
                            FoodsState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getGetFoodsState.update {
                            FoodsState(foodsUIData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getGetFoodsState.update {
                            FoodsState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getGetFoodsState.update {
                        FoodsState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }
    fun makeGetCategoryCall() {
        makeGetCategoryCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getCategoryState.update {
                            CategoryTagsState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getCategoryState.update {
                            CategoryTagsState(getCategoryTagsUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getCategoryState.update {
                            CategoryTagsState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getCategoryState.update {
                        CategoryTagsState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }

    fun makeGetTagsCall() {
        makeGetTagsCallsUseCase().onEach { response ->
            try {
                when (response) {
                    is NetworkResult.Loading -> {
                        _getCategoryState.update {
                            CategoryTagsState(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        _getCategoryState.update {
                            CategoryTagsState(getCategoryTagsUiData = response.data, isSuccess = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _getCategoryState.update {
                            CategoryTagsState(error = response.message, isError = true)
                        }
                    }

                    else -> {}
                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _getCategoryState.update {
                        CategoryTagsState(error = response.message, isError = true)
                    }
                } else {
                    throw t
                }

            }
        }.launchIn(viewModelScope)
    }
}
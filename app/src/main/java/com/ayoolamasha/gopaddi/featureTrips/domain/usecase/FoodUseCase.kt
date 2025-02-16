package com.ayoolamasha.gopaddi.featureTrips.domain.usecase

import com.ayoolamasha.gopaddi.di.IoDispatcher
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.FoodsDomainRepositoryHelper
import com.ayoolamasha.gopaddi.network.BaseUseCase
import com.ayoolamasha.gopaddi.network.requireParams
import com.firstcentralcreditbureau.fcbmobile.network.mappers.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MakeCreateFoodCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: FoodsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<CreateFoodData, NetworkResult<CreateFoodUiData>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: CreateFoodData?): Flow<NetworkResult<CreateFoodUiData>> {
        requireParams(params)
        return domainRepositoryHelper.createFood(createFoodData = params)
    }
}

@Singleton
class MakeGetFoodCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: FoodsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<GetFoodUiData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<GetFoodUiData>>> {
        return domainRepositoryHelper.getFoods()
    }
}

@Singleton
class MakeGetCategoryCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: FoodsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<GetCategoryTagsUIData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<GetCategoryTagsUIData>>> {
        return domainRepositoryHelper.getCategory()
    }
}

@Singleton
class MakeGetTagsCallsUseCase @Inject constructor(
    private val domainRepositoryHelper: FoodsDomainRepositoryHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Any, NetworkResult<List<GetCategoryTagsUIData>>>() {
    override val dispatcher: CoroutineDispatcher
        get() = ioDispatcher

    override fun execute(params: Any?): Flow<NetworkResult<List<GetCategoryTagsUIData>>> {
        return domainRepositoryHelper.getTags()
    }
}

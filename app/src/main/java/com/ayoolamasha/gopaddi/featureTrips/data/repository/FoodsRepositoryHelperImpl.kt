package com.ayoolamasha.gopaddi.featureTrips.data.repository

import com.ayoolamasha.gopaddi.apiService.FoodApiServiceHelper
import com.ayoolamasha.gopaddi.di.IoDispatcher
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.createFoodMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getCategoryTagMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.getFoodMapper
import com.ayoolamasha.gopaddi.featureTrips.data.mapper.toMultipart
import com.ayoolamasha.gopaddi.featureTrips.data.model.CreateFoodData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CreateFoodUiData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.ayoolamasha.gopaddi.network.extensions.call
import com.ayoolamasha.gopaddi.network.mappers.Either
import com.ayoolamasha.gopaddi.network.middleware.MiddlewareProvider
import com.ayoolamasha.gopaddi.network.model.Failure
import com.ayoolamasha.gopaddi.network.model.ResponseMessage
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodsRepositoryHelperImpl @Inject constructor(
    private val middlewareProvider: MiddlewareProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val errorAdapter: JsonAdapter<ResponseMessage>,
    private val foodApiServiceHelper: FoodApiServiceHelper
):FoodsRepositoryHelper{
    override suspend fun createFoods(createFoodData: CreateFoodData): Either<Failure, CreateFoodUiData> {
        val (requestBodyMap, imageParts) = createFoodData.toMultipart()
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                foodApiServiceHelper.createFood(requestBodyMap = requestBodyMap, images = imageParts)
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.createFoodMapper()
            }

        } as Either<Failure, CreateFoodUiData>
    }

    override suspend fun getFoods(): Either<Failure, List<GetFoodUiData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                foodApiServiceHelper.getFoods()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.getFoodMapper()
            }

        } as Either<Failure, List<GetFoodUiData>>
    }

    override suspend fun getCategory(): Either<Failure, List<GetCategoryTagsUIData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                foodApiServiceHelper.getFoodCategories()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.getCategoryTagMapper()
            }

        } as Either<Failure, List<GetCategoryTagsUIData>>
    }

    override suspend fun getTags(): Either<Failure, List<GetCategoryTagsUIData>> {
        return call(
            middleWares = middlewareProvider.getAll(),
            ioDispatcher = ioDispatcher,
            adapter = errorAdapter,
            retrofitCall = {
                foodApiServiceHelper.getFoodTags()
            }
        ).let { response ->
            response.mapSuccess { responseData ->
                responseData
            }.coMapSuccess { responseList ->
                responseList?.getCategoryTagMapper()
            }

        } as Either<Failure, List<GetCategoryTagsUIData>>
    }

}
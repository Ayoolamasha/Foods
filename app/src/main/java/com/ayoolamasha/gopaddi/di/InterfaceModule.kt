package com.ayoolamasha.gopaddi.di


import com.ayoolamasha.gopaddi.apiService.FoodApiServiceHelper
import com.ayoolamasha.gopaddi.apiService.FoodApiServiceHelperImpl
import com.ayoolamasha.gopaddi.featureTrips.data.repository.FoodsRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.data.repository.FoodsRepositoryHelperImpl
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.FoodsDomainRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.FoodsDomainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class InterfaceModule {

    @Binds
    abstract fun providesApiServiceHelper(tripApiServiceHelperImpl: FoodApiServiceHelperImpl): FoodApiServiceHelper

    @Binds
    abstract fun providesTripDomainRepositoryHelper(tripsDomainRepositoryImpl: FoodsDomainRepositoryImpl): FoodsDomainRepositoryHelper

    @Binds
    abstract fun providesTripRepositoryHelper(tripRepositoryHelperImpl: FoodsRepositoryHelperImpl): FoodsRepositoryHelper
}

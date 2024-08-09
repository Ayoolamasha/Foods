package com.ayoolamasha.gopaddi.di


import com.ayoolamasha.gopaddi.apiService.TripApiServiceHelper
import com.ayoolamasha.gopaddi.apiService.TripApiServiceHelperImpl
import com.ayoolamasha.gopaddi.featureTrips.data.repository.TripRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.data.repository.TripRepositoryHelperImpl
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.TripsDomainRepositoryHelper
import com.ayoolamasha.gopaddi.featureTrips.domain.repository.TripsDomainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class InterfaceModule {

    @Binds
    abstract fun providesApiServiceHelper(tripApiServiceHelperImpl: TripApiServiceHelperImpl): TripApiServiceHelper

    @Binds
    abstract fun providesTripDomainRepositoryHelper(tripsDomainRepositoryImpl: TripsDomainRepositoryImpl): TripsDomainRepositoryHelper

    @Binds
    abstract fun providesTripRepositoryHelper(tripRepositoryHelperImpl: TripRepositoryHelperImpl): TripRepositoryHelper
}

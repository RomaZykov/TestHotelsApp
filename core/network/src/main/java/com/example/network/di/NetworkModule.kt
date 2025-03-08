package com.example.network.di

import com.example.network.HotelNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindsHotelNetworkDataSource(impl: HotelNetworkDataSource.Base): HotelNetworkDataSource
}
package com.example.data.di

import com.example.data.HotelsRepositoryImpl
import com.example.domain.repository.HotelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelScope {
    @Binds
    abstract fun bindsHotelsRepository(
        hotelsRepositoryImpl: HotelsRepositoryImpl
    ): HotelsRepository
}
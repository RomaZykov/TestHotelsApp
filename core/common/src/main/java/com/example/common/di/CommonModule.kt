package com.example.common.di

import com.example.common.ManageResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    abstract fun bindManageResource(resources: ManageResources.Base): ManageResources
}
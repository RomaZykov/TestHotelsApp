package com.example.exceptions.di

import com.example.exceptions.HandleError
import com.example.exceptions.HandleNetworkException
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExceptionModule {
    @Binds
    abstract fun bindNetworkHandleError(handleNetworkException: HandleNetworkException): HandleError
}
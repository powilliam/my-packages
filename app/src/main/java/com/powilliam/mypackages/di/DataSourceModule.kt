package com.powilliam.mypackages.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(): AuthRemoteDataSource =
        AuthRemoteDataSourceImpl(Firebase.auth)
}
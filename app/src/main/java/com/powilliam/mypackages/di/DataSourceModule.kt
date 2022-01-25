package com.powilliam.mypackages.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSourceImpl
import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSource
import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSourceImpl
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

    @Singleton
    @Provides
    fun provideFeatureFlagRemoteDataSource(): FeatureFlagRemoteDataSource =
        FeatureFlagRemoteDataSourceImpl(Firebase.remoteConfig)
}
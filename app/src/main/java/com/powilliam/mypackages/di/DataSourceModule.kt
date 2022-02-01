package com.powilliam.mypackages.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.powilliam.mypackages.data.datasource.*
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

    @Singleton
    @Provides
    fun providePackageRemoteDataSource(): PackageRemoteDataSource =
        PackageRemoteDataSourceImpl(Firebase.database.reference)

    @Singleton
    @Provides
    fun provideDeviceTokenLocalDataSource(): DeviceTokenLocalDataSource =
        DeviceTokenLocalDataSourceImpl(Firebase.messaging)

    @Singleton
    @Provides
    fun provideUserSettingsRemoteDataSource(): UserSettingsRemoteDataSource =
        UserSettingsRemoteDataSourceImpl(Firebase.database.reference)
}
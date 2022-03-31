package com.powilliam.mypackages.di

import com.powilliam.mypackages.data.datasource.*
import com.powilliam.mypackages.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePackageRepository(
        packageRemoteDataSource: PackageRemoteDataSource
    ): PackageRepository = PackageRepositoryImpl(packageRemoteDataSource)

    @Singleton
    @Provides
    fun provideNotificationRepository(
        notificationLocalDataSource: NotificationLocalDataSource
    ): NotificationRepository = NotificationRepositoryImpl(notificationLocalDataSource)
}
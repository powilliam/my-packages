package com.powilliam.mypackages.di

import com.powilliam.mypackages.data.repository.NotificationRepository
import com.powilliam.mypackages.domain.usecase.CountUnVisualizedNotificationsUseCase
import com.powilliam.mypackages.domain.usecase.CountUnVisualizedNotificationsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideCountUnVisualizedNotificationsUseCase(
        notificationRepository: NotificationRepository
    ): CountUnVisualizedNotificationsUseCase =
        CountUnVisualizedNotificationsUseCaseImpl(notificationRepository)
}
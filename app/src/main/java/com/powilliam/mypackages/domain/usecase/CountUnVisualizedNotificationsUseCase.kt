package com.powilliam.mypackages.domain.usecase

import com.powilliam.mypackages.data.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CountUnVisualizedNotificationsUseCase {
    suspend fun execute(receiverId: String): Flow<Int>
}

class CountUnVisualizedNotificationsUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : CountUnVisualizedNotificationsUseCase {
    override suspend fun execute(receiverId: String): Flow<Int> = notificationRepository
        .loadNotificationsByReceiverId(receiverId)
        .map { notifications -> notifications.filter { notification -> notification.hasVisualized.not() } }
        .map { notifications -> notifications.count() }
}
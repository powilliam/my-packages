package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.NotificationLocalDataSource
import com.powilliam.mypackages.data.entity.Notification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotificationRepository {
    suspend fun loadNotificationsByReceiverId(receiverId: String): Flow<List<Notification>>
    suspend fun insertNotification(notification: Notification)
    suspend fun markAllNotificationsAsVisualized()
    suspend fun deleteAllNotifications(receiverId: String)
}

class NotificationRepositoryImpl @Inject constructor(
    private val notificationLocalDataSource: NotificationLocalDataSource
) : NotificationRepository {
    override suspend fun loadNotificationsByReceiverId(receiverId: String): Flow<List<Notification>> =
        notificationLocalDataSource.loadNotificationsByReceiverId(receiverId)

    override suspend fun insertNotification(notification: Notification) =
        notificationLocalDataSource.insertNotification(notification)

    override suspend fun markAllNotificationsAsVisualized() =
        notificationLocalDataSource.markAllNotificationsAsVisualized()

    override suspend fun deleteAllNotifications(receiverId: String) =
        notificationLocalDataSource.deleteAllNotifications(receiverId)
}
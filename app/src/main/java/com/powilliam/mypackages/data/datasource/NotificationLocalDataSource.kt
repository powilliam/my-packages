package com.powilliam.mypackages.data.datasource

import com.powilliam.mypackages.data.database.NotificationAccessObject
import com.powilliam.mypackages.data.entity.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotificationLocalDataSource {
    suspend fun loadNotificationsByReceiverId(receiverId: String): Flow<List<Notification>>
    suspend fun insertNotification(notification: Notification)
    suspend fun markAllNotificationsAsVisualized()
}

class NotificationLocalDataSourceImpl @Inject constructor(
    private val notificationAccessObject: NotificationAccessObject
) : NotificationLocalDataSource {
    override suspend fun loadNotificationsByReceiverId(receiverId: String): Flow<List<Notification>> {
        return withContext(Dispatchers.IO) {
            notificationAccessObject.loadNotificationsByReceiverId(receiverId)
        }
    }

    override suspend fun insertNotification(notification: Notification) {
        withContext(Dispatchers.IO) {
            notificationAccessObject.insertNotification(notification)
        }
    }

    override suspend fun markAllNotificationsAsVisualized() {
        withContext(Dispatchers.IO) {
            notificationAccessObject.markAllNotificationsAsVisualized()
        }
    }
}
package com.powilliam.mypackages.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.powilliam.mypackages.data.entity.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationAccessObject {
    @Query("SELECT * FROM Notification WHERE notification_receiver_id = :receiverId")
    fun loadNotificationsByReceiverId(receiverId: String): Flow<List<Notification>>

    @Insert
    suspend fun insertNotification(notification: Notification)

    @Query("UPDATE Notification SET notification_has_visualized = 1")
    suspend fun markAllNotificationsAsVisualized()
}
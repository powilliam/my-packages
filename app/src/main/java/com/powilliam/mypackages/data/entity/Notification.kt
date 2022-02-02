package com.powilliam.mypackages.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "notification_receiver_id") val receiverId: String,
    @ColumnInfo(name = "notification_title") val title: String,
    @ColumnInfo(name = "notification_body") val body: String,
    @ColumnInfo(name = "notification_has_visualized") val hasVisualized: Boolean = false
)

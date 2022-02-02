package com.powilliam.mypackages.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.powilliam.mypackages.data.entity.Notification

@Database(entities = [Notification::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notification(): NotificationAccessObject
}
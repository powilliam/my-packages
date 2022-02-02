package com.powilliam.mypackages.di

import android.content.Context
import androidx.room.Room
import com.powilliam.mypackages.data.database.AppDatabase
import com.powilliam.mypackages.data.database.NotificationAccessObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-database.db").build()

    @Singleton
    @Provides
    fun provideNotificationAccessObject(database: AppDatabase): NotificationAccessObject =
        database.notification()
}
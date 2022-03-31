package com.powilliam.mypackages.di

import android.content.Context
import androidx.work.WorkManager
import com.powilliam.mypackages.data.database.NotificationAccessObject
import com.powilliam.mypackages.data.datasource.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun providePackageRemoteDataSource(): PackageRemoteDataSource = PackageRemoteDataSourceImpl()

    @Singleton
    @Provides
    fun provideNotificationLocalDataSource(
        notificationAccessObject: NotificationAccessObject
    ): NotificationLocalDataSource = NotificationLocalDataSourceImpl(notificationAccessObject)

    @Singleton
    @Provides
    fun provideNotificationTaskDataSource(@ApplicationContext context: Context): NotificationTaskDataSource =
        NotificationTaskDataSourceImpl(WorkManager.getInstance(context))
}
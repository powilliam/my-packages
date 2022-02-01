package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.DeviceTokenLocalDataSource
import com.powilliam.mypackages.data.datasource.UserSettingsRemoteDataSource
import javax.inject.Inject

interface UserSettingsRepository {
    suspend fun updateDeviceTokenSettings(userId: String)
}

class UserSettingsRepositoryImpl @Inject constructor(
    private val deviceTokenLocalDataSource: DeviceTokenLocalDataSource,
    private val userSettingsRemoteDataSource: UserSettingsRemoteDataSource,
) : UserSettingsRepository {
    override suspend fun updateDeviceTokenSettings(userId: String) {
        val token = deviceTokenLocalDataSource.getDeviceToken()
        userSettingsRemoteDataSource.updateDeviceTokenSettings(userId, token)
    }
}
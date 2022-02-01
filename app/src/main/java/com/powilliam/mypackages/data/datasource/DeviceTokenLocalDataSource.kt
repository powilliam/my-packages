package com.powilliam.mypackages.data.datasource

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeviceTokenLocalDataSource {
    suspend fun getDeviceToken(): String
}

class DeviceTokenLocalDataSourceImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : DeviceTokenLocalDataSource {
    override suspend fun getDeviceToken(): String {
        return withContext(Dispatchers.IO) {
            firebaseMessaging.token.await()
        }
    }
}
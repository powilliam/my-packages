package com.powilliam.mypackages.data.datasource

import com.google.firebase.database.DatabaseReference
import com.powilliam.mypackages.data.constants.FirebaseReferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserSettingsRemoteDataSource {
    suspend fun updateDeviceTokenSettings(userId: String, newToken: String)
}

class UserSettingsRemoteDataSourceImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference,
) : UserSettingsRemoteDataSource {
    override suspend fun updateDeviceTokenSettings(userId: String, newToken: String) {
        withContext(Dispatchers.IO) {
            firebaseDatabase
                .child(FirebaseReferences.USERS_REFERENCE)
                .child(userId)
                .child(FirebaseReferences.USER_SETTINGS_REFERENCE)
                .child(FirebaseReferences.DEVICE_TOKEN_REFERENCE)
                .setValue(newToken)
        }
    }
}
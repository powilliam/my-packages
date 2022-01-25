package com.powilliam.mypackages.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.powilliam.mypackages.data.entity.Feature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FeatureFlagRemoteDataSource {
    suspend fun isFeatureEnabled(feature: Feature): Boolean
}

class FeatureFlagRemoteDataSourceImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : FeatureFlagRemoteDataSource {
    override suspend fun isFeatureEnabled(feature: Feature): Boolean = withContext(Dispatchers.IO) {
        firebaseRemoteConfig.getBoolean(feature.flag)
    }
}
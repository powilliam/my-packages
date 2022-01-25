package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSource
import com.powilliam.mypackages.data.entity.Feature
import javax.inject.Inject

interface FeatureFlagRepository {
    suspend fun isAuthFeatureEnabled(): Boolean
}

class FeatureFlagRepositoryImpl @Inject constructor(
    private val featureFlagRemoteDataSource: FeatureFlagRemoteDataSource
) : FeatureFlagRepository {
    override suspend fun isAuthFeatureEnabled(): Boolean =
        featureFlagRemoteDataSource.isFeatureEnabled(Feature.Auth)
}
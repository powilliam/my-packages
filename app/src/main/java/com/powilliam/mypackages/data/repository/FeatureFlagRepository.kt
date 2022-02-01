package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSource
import com.powilliam.mypackages.data.entity.Feature
import com.powilliam.mypackages.utils.withInterval
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FeatureFlagRepository {
    suspend fun isAuthFeatureEnabled(): Flow<Boolean>
}

class FeatureFlagRepositoryImpl @Inject constructor(
    private val featureFlagRemoteDataSource: FeatureFlagRemoteDataSource
) : FeatureFlagRepository {
    override suspend fun isAuthFeatureEnabled(): Flow<Boolean> = withInterval(
        ONE_SECOND_IN_MILLISECONDS
    ) {
        featureFlagRemoteDataSource.isFeatureEnabled(Feature.Auth)
    }

    companion object {
        const val ONE_SECOND_IN_MILLISECONDS = 1000L
    }
}
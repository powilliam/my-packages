package com.powilliam.mypackages.di

import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSource
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.AuthRepositoryImpl
import com.powilliam.mypackages.data.repository.FeatureFlagRepository
import com.powilliam.mypackages.data.repository.FeatureFlagRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository =
        AuthRepositoryImpl(authRemoteDataSource)

    @Singleton
    @Provides
    fun provideFeatureFlagRepository(
        featureFlagRemoteDataSource: FeatureFlagRemoteDataSource
    ): FeatureFlagRepository = FeatureFlagRepositoryImpl(featureFlagRemoteDataSource)
}
package com.powilliam.mypackages.di

import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.FeatureFlagRemoteDataSource
import com.powilliam.mypackages.data.datasource.PackageRemoteDataSource
import com.powilliam.mypackages.data.repository.*
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

    @Singleton
    @Provides
    fun providePackageRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        packageRemoteDataSource: PackageRemoteDataSource,
    ): PackageRepository = PackageRepositoryImpl(
        authRemoteDataSource,
        packageRemoteDataSource,
    )
}
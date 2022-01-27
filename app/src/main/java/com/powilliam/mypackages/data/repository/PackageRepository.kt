package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.PackageRemoteDataSource
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.data.mappers.PackageMapper
import javax.inject.Inject

interface PackageRepository {
    suspend fun insert(newPackage: Package)
}

class PackageRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val packageRemoteDataSource: PackageRemoteDataSource,
    private val packageToMapMapper: PackageMapper
) : PackageRepository {
    override suspend fun insert(newPackage: Package) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        val packageAsMap = packageToMapMapper.toMap(newPackage)
        packageRemoteDataSource.insert(account.uid, packageAsMap)
    }
}
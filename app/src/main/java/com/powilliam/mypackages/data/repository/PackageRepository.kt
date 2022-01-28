package com.powilliam.mypackages.data.repository

import android.util.Log
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.PackageRemoteDataSource
import com.powilliam.mypackages.data.entity.Package
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface PackageRepository {
    suspend fun all(): Flow<List<Package>>
    suspend fun insert(newPackage: Package)
}

class PackageRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val packageRemoteDataSource: PackageRemoteDataSource,
) : PackageRepository {
    override suspend fun all(): Flow<List<Package>> {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return emptyFlow()
        return packageRemoteDataSource.all(account.uid)
            .map { packages -> packages.values.toList() as List<Map<String, Any>> }
            .map { packages -> packages.map { Package.fromMap(it) } }
            .filter { packages -> packages.all { pkg -> pkg.events.isNotEmpty() } }
    }

    override suspend fun insert(newPackage: Package) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        packageRemoteDataSource.insert(account.uid, newPackage.toMap())
    }
}
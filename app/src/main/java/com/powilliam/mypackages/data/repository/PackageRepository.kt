package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.datasource.PackageRemoteDataSource
import com.powilliam.mypackages.data.entity.Package
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface PackageRepository {
    suspend fun all(): Flow<List<Package>>
    suspend fun one(tracker: String): Flow<Package>
    suspend fun insert(newPackage: Package)
    suspend fun update(tracker: String, name: String)
    suspend fun remove(tracker: String)
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

    override suspend fun one(tracker: String): Flow<Package> {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return emptyFlow()
        return packageRemoteDataSource.one(account.uid, tracker)
            .map { entity -> Package.fromMap(entity) }
    }

    override suspend fun insert(newPackage: Package) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        packageRemoteDataSource.insert(account.uid, newPackage.toMap())
    }

    override suspend fun update(tracker: String, name: String) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        packageRemoteDataSource.update(account.uid, tracker, name)
    }

    override suspend fun remove(tracker: String) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        packageRemoteDataSource.remove(account.uid, tracker)
    }
}
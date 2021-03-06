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
    suspend fun delete(tracker: String)
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

    override suspend fun delete(tracker: String) {
        val account = authRemoteDataSource.getAuthenticatedAccount() ?: return
        packageRemoteDataSource.delete(account.uid, tracker)
    }
}
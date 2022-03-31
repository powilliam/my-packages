package com.powilliam.mypackages.data.repository

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
    private val packageRemoteDataSource: PackageRemoteDataSource,
) : PackageRepository {
    override suspend fun all(): Flow<List<Package>> = flow { }

    override suspend fun one(tracker: String): Flow<Package> = flow { }

    override suspend fun insert(newPackage: Package) {
        packageRemoteDataSource.insert("", mapOf())
    }

    override suspend fun update(tracker: String, name: String) {
        packageRemoteDataSource.update("", tracker, name)
    }

    override suspend fun delete(tracker: String) {
        packageRemoteDataSource.delete("", tracker)
    }
}
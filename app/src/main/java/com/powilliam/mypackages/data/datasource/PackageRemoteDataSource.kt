package com.powilliam.mypackages.data.datasource

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PackageRemoteDataSource {
    suspend fun all(userId: String): Flow<Map<String, Any?>>
    suspend fun one(userId: String, tracker: String): Flow<Map<String, Any?>>
    suspend fun insert(userId: String, newPackage: Map<String, Any?>)
    suspend fun update(userId: String, tracker: String, newName: String)
    suspend fun delete(userId: String, tracker: String)
}

class PackageRemoteDataSourceImpl @Inject constructor() : PackageRemoteDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun all(userId: String): Flow<Map<String, Any?>> = flow {}

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun one(userId: String, tracker: String): Flow<Map<String, Any?>> = flow {}

    override suspend fun insert(userId: String, newPackage: Map<String, Any?>) {
        withContext(Dispatchers.IO) {
        }
    }

    override suspend fun update(userId: String, tracker: String, newName: String) {
        withContext(Dispatchers.IO) {
        }
    }

    override suspend fun delete(userId: String, tracker: String) {
        withContext(Dispatchers.IO) {
        }
    }
}
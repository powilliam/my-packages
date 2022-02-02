package com.powilliam.mypackages.data.datasource

import com.google.firebase.database.*
import com.powilliam.mypackages.data.constants.FirebaseReferences
import com.powilliam.mypackages.utils.asRealtimeFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PackageRemoteDataSource {
    suspend fun all(userId: String): Flow<Map<String, Any?>>
    suspend fun one(userId: String, tracker: String): Flow<Map<String, Any?>>
    suspend fun insert(userId: String, newPackage: Map<String, Any?>)
    suspend fun update(userId: String, tracker: String, newName: String)
    suspend fun delete(userId: String, tracker: String)
}

class PackageRemoteDataSourceImpl @Inject constructor(
    private val realtimeDatabase: DatabaseReference,
) : PackageRemoteDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun all(userId: String): Flow<Map<String, Any?>> =
        reference(userId)
            .asRealtimeFlow<Map<String, Any?>>()
            .flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun one(userId: String, tracker: String): Flow<Map<String, Any?>> =
        reference(userId).child(tracker)
            .asRealtimeFlow<Map<String, Any?>>()
            .flowOn(Dispatchers.IO)

    override suspend fun insert(userId: String, newPackage: Map<String, Any?>) {
        withContext(Dispatchers.IO) {
            val packageReference = reference(userId)
                .child(newPackage["tracker"] as String)
            val insertedPackages = packageReference
                .get()
                .await()
                .getValue(Map::class.java)
            if (insertedPackages.isNullOrEmpty()) {
                packageReference.setValue(newPackage)
            }
        }
    }

    override suspend fun update(userId: String, tracker: String, newName: String) {
        withContext(Dispatchers.IO) {
            reference(userId).child(tracker).child("name").setValue(newName)
        }
    }

    override suspend fun delete(userId: String, tracker: String) {
        withContext(Dispatchers.IO) {
            reference(userId).child(tracker).removeValue()
        }
    }

    private fun reference(userId: String): DatabaseReference =
        realtimeDatabase.child(FirebaseReferences.USERS_REFERENCE)
            .child(userId)
            .child(FirebaseReferences.PACKAGES_REFERENCE)
}
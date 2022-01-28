package com.powilliam.mypackages.data.datasource

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.powilliam.mypackages.data.constants.FirebaseReferences
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface PackageRemoteDataSource {
    suspend fun all(userId: String): Flow<Map<String, Any?>>
    suspend fun insert(userId: String, newPackage: Map<String, Any?>)
}

class PackageRemoteDataSourceImpl @Inject constructor(
    private val realtimeDatabase: DatabaseReference,
    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
) : PackageRemoteDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun all(userId: String): Flow<Map<String, Any?>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val packages = snapshot.getValue<Map<String, Any>>() ?: return
                coroutineScope.launch {
                    send(packages)
                }
            }

            override fun onCancelled(error: DatabaseError) = cancel()
        }
        reference(userId).addValueEventListener(listener)
        awaitClose { reference(userId).removeEventListener(listener) }
    }

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

    private fun reference(userId: String): DatabaseReference =
        realtimeDatabase.child(FirebaseReferences.USERS_REFERENCE)
            .child(userId)
            .child(FirebaseReferences.PACKAGES_REFERENCE)
}
package com.powilliam.mypackages.data.datasource

import com.google.firebase.database.DatabaseReference
import com.powilliam.mypackages.data.constants.FirebaseReferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PackageRemoteDataSource {
    suspend fun insert(userId: String, newPackage: Map<String, Any>)
}

class PackageRemoteDataSourceImpl @Inject constructor(
    private val realtimeDatabase: DatabaseReference
) : PackageRemoteDataSource {
    override suspend fun insert(userId: String, newPackage: Map<String, Any>) {
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
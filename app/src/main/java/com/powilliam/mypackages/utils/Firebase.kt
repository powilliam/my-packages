package com.powilliam.mypackages.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Any> DatabaseReference.asRealtimeFlow(
    coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
): Flow<T> = callbackFlow {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val value = snapshot.value as T? ?: return
            coroutineScope.launch {
                send(value)
            }
        }

        override fun onCancelled(error: DatabaseError) = cancel()
    }
    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}
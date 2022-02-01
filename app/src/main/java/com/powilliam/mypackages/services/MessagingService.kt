package com.powilliam.mypackages.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.powilliam.mypackages.data.constants.FirebaseReferences

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        val account = Firebase.auth.currentUser ?: return
        Firebase.database.reference
            .child(FirebaseReferences.USERS_REFERENCE)
            .child(account.uid)
            .child(FirebaseReferences.DEVICE_TOKEN_REFERENCE)
            .setValue(token)
    }
}
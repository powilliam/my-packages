package com.powilliam.mypackages.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class AuthProvider {
    data class GoogleProvider(val idToken: String?) : AuthProvider()
}

interface AuthRemoteDataSource {
    suspend fun getAuthenticatedAccount(): FirebaseUser?
    suspend fun authenticate(provider: AuthProvider): AuthResult
    suspend fun unAuthenticate()
}

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {
    override suspend fun getAuthenticatedAccount(): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            firebaseAuth.currentUser
        }
    }

    override suspend fun authenticate(provider: AuthProvider): AuthResult {
        return withContext(Dispatchers.IO) {
            when (provider) {
                is AuthProvider.GoogleProvider -> onAuthenticateWithGoogleProvider(provider.idToken)
            }
        }
    }

    override suspend fun unAuthenticate() {
        withContext(Dispatchers.IO) {
            firebaseAuth.signOut()
        }
    }

    private suspend fun onAuthenticateWithGoogleProvider(idToken: String?): AuthResult {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        return firebaseAuth.signInWithCredential(credentials).await()
    }
}
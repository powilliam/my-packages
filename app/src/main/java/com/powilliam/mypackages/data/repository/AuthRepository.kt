package com.powilliam.mypackages.data.repository

import com.google.firebase.auth.AuthResult
import com.powilliam.mypackages.data.datasource.AuthProvider
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.entity.UserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun getAuthenticatedAccount(): Flow<UserEntity?>
    suspend fun authenticateWithGoogleSignIn(idToken: String?): AuthResult
    suspend fun unAuthenticate()
}

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun getAuthenticatedAccount() = flow {
        while (true) {
            delay(ONE_SECOND_IN_MILLISECONDS)
            val account = authRemoteDataSource.getAuthenticatedAccount()
            account?.let {
                emit(UserEntity(id = it.uid, email = it.email, avatar = it.photoUrl))
            }
        }
    }

    override suspend fun authenticateWithGoogleSignIn(idToken: String?) =
        authRemoteDataSource.authenticate(AuthProvider.GoogleProvider(idToken))

    override suspend fun unAuthenticate() =
        authRemoteDataSource.unAuthenticate()

    companion object {
        private const val ONE_SECOND_IN_MILLISECONDS: Long = 1000
    }
}
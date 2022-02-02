package com.powilliam.mypackages.data.repository

import com.powilliam.mypackages.data.datasource.AuthProvider
import com.powilliam.mypackages.data.datasource.AuthRemoteDataSource
import com.powilliam.mypackages.data.entity.UserEntity
import com.powilliam.mypackages.utils.withInterval
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun getAuthenticatedAccount(): Flow<UserEntity?>
    suspend fun authenticateWithGoogleSignIn(idToken: String?): UserEntity?
    suspend fun unAuthenticate()
}

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun getAuthenticatedAccount(): Flow<UserEntity?> =
        withInterval(ONE_SECOND_IN_MILLISECONDS) {
            val account = authRemoteDataSource.getAuthenticatedAccount() ?: return@withInterval null
            UserEntity(id = account.uid, email = account.email, avatar = account.photoUrl)
        }

    override suspend fun authenticateWithGoogleSignIn(idToken: String?): UserEntity? {
        val result = authRemoteDataSource.authenticate(AuthProvider.GoogleProvider(idToken))
        return if (result.user != null) UserEntity(
            id = result.user!!.uid,
            email = result.user!!.email,
            avatar = result.user!!.photoUrl
        ) else null
    }

    override suspend fun unAuthenticate() =
        authRemoteDataSource.unAuthenticate()

    companion object {
        private const val ONE_SECOND_IN_MILLISECONDS = 1000L
    }
}
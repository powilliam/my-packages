package com.powilliam.mypackages.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.powilliam.mypackages.R

@Retention(AnnotationRetention.BINARY)
annotation class OneTapSignInRequest

@Retention(AnnotationRetention.BINARY)
annotation class GoogleIdTokenRequestOptions

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {
    @GoogleIdTokenRequestOptions
    @Singleton
    @Provides
    fun provideGoogleIdTokenRequestOptions(@ApplicationContext context: Context): BeginSignInRequest.GoogleIdTokenRequestOptions =
        BeginSignInRequest.GoogleIdTokenRequestOptions
            .Builder()
            .setSupported(true)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

    @OneTapSignInRequest
    @Singleton
    @Provides
    fun provideOneTapSignInRequest(
        @GoogleIdTokenRequestOptions googleIdTokenRequestOptions: BeginSignInRequest.GoogleIdTokenRequestOptions
    ): BeginSignInRequest = BeginSignInRequest
        .Builder()
        .setGoogleIdTokenRequestOptions(googleIdTokenRequestOptions)
        .setAutoSelectEnabled(false)
        .build()
}
package com.powilliam.mypackages.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.powilliam.mypackages.R

@Retention(AnnotationRetention.BINARY)
annotation class FirebaseGoogleSignInOptions

@Retention(AnnotationRetention.BINARY)
annotation class FirebaseGoogleSignInClient

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {
    @FirebaseGoogleSignInOptions
    @Singleton
    @Provides
    fun provideGoogleSignInOptions(
        @ApplicationContext context: Context
    ): GoogleSignInOptions = GoogleSignInOptions
        .Builder()
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    @FirebaseGoogleSignInClient
    @Singleton
    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        @FirebaseGoogleSignInOptions options: GoogleSignInOptions
    ): GoogleSignInClient = GoogleSignIn.getClient(context, options)
}
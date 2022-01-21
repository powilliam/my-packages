package com.powilliam.mypackages.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.powilliam.mypackages.di.OneTapSignInRequest
import com.powilliam.mypackages.ui.theming.MyPackagesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MyPackagesActivity : ComponentActivity() {
    @OneTapSignInRequest
    @Inject
    lateinit var oneTapSignInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Content()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        ProvideWindowInsets {
            MyPackagesTheme({ this }) {
                NavigationGraph { beginSignIn() }
            }
        }
    }

    private suspend fun beginSignIn(): IntentSenderRequest {
        val signIn = Identity.getSignInClient(this).beginSignIn(oneTapSignInRequest).await()
        return IntentSenderRequest.Builder(signIn.pendingIntent.intentSender).build()
    }
}
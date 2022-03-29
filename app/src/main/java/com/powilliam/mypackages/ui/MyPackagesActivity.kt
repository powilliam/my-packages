package com.powilliam.mypackages.ui

import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powilliam.mypackages.di.FirebaseGoogleSignInClient
import com.powilliam.mypackages.ui.theming.MyPackagesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPackagesActivity : ComponentActivity() {
    @FirebaseGoogleSignInClient
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

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

    private fun createGoogleSignInPendingIntent(): PendingIntent {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return PendingIntent.getActivity(
            this,
            GOOGLE_SIGN_IN_REQUEST_CODE,
            googleSignInClient.signInIntent,
            flags
        )
    }


    private fun beginSignIn(): IntentSenderRequest {
        val pendingIntent = createGoogleSignInPendingIntent()
        return IntentSenderRequest.Builder(pendingIntent).build()
    }

    companion object {
        private const val GOOGLE_SIGN_IN_REQUEST_CODE = 1001
    }
}
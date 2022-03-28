package com.powilliam.mypackages.ui.routes

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.powilliam.mypackages.ui.Destination
import com.powilliam.mypackages.ui.screens.PackagesMapScreen
import com.powilliam.mypackages.ui.viewmodels.PackagesMapUiState
import com.powilliam.mypackages.ui.viewmodels.PackagesMapViewModel
import kotlinx.coroutines.launch

@Composable
fun PackagesMapRoute(
    navController: () -> NavController,
    beginSignIn: suspend () -> IntentSenderRequest,
) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<PackagesMapViewModel>()
    val uiState by viewModel.uiState.collectAsState(PackagesMapUiState())
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            val credentials = Identity
                .getSignInClient(context)
                .getSignInCredentialFromIntent(result.data)
            viewModel.onAuthenticateWithGoogleSignIn(credentials.googleIdToken)
        } catch (exception: Exception) {
            Log.e("GoogleSignIn", exception.message ?: "Failed to SignIn")
            viewModel.onFailToAuthenticateWithGoogleSignIn(exception)
        }
    }
    val launchSignIn = {
        coroutineScope.launch {
            try {
                launcher.launch(beginSignIn())
            } catch (exception: Exception) {
                Log.e("GoogleSignIn", exception.message ?: "Failed to SignIn")
            }
        }
    }

    LaunchedEffect(uiState.account) {
        uiState.account?.let { account ->
            viewModel.onCollectPackagesBasedOnSignedAccount()
            viewModel.onCollectNotificationsCount(account.id)
        }
    }

    LaunchedEffect(uiState.hasFailedToAuthenticate, uiState.authenticationFailureReason) {
        Toast.makeText(context, uiState.authenticationFailureReason, Toast.LENGTH_LONG).show()
    }

    PackagesMapScreen(
        uiState = uiState,
        onChangeAccount = { launchSignIn() },
        onSignOut = { viewModel.onSignOut() },
        onFocusAtOnePackage = viewModel::onFocusAtOnePackage,
        onLaunchOneTapSignIn = { launchSignIn() },
        onNavigateToSearchPackageScreen = {
            if (uiState.shouldPromptSignIn) {
                launchSignIn()
            } else {
                navController().navigate(
                    Destination.SearchPackage.route
                )
            }
        },
        onNavigateToAddPackageScreen = {
            if (uiState.shouldPromptSignIn) {
                launchSignIn()
            } else {
                navController().navigate(Destination.AddPackage.route)
            }
        },
        onNavigateToPackageScreen = {
            if (uiState.shouldPromptSignIn) {
                launchSignIn()
            } else {
                navController().navigate(
                    // TODO: Create an method to replace it
                    Destination.Package.route.replace(
                        "{tracker}",
                        it.tracker
                    )
                )
            }
        },
        onNavigateToNotificationsScreen = {
            if (uiState.shouldPromptSignIn) {
                launchSignIn()
            } else {
                navController().navigate(Destination.Notifications.route)
            }
        }
    )
}
package com.powilliam.mypackages.ui.routes

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.powilliam.mypackages.ui.Destination
import com.powilliam.mypackages.ui.screens.PackagesMapScreen
import com.powilliam.mypackages.ui.viewmodels.PackagesMapUiState
import com.powilliam.mypackages.ui.viewmodels.PackagesMapViewModel

@Composable
fun PackagesMapRoute(
    navController: () -> NavController,
    beginSignIn: () -> IntentSenderRequest,
) {
    val viewModel = hiltViewModel<PackagesMapViewModel>()
    val uiState by viewModel.uiState.collectAsState(PackagesMapUiState())
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            val account = GoogleSignIn
                .getSignedInAccountFromIntent(result.data)
                .getResult(ApiException::class.java)
            viewModel.onAuthenticateWithGoogleSignIn(account.idToken)
        } catch (exception: Exception) {
            Log.e("GoogleSignIn", exception.message ?: "Failed to SignIn")
        }
    }
    val launchSignIn = {
        try {
            launcher.launch(beginSignIn())
        } catch (exception: Exception) {
            Log.e("GoogleSignIn", exception.message ?: "Failed to SignIn")
        }
    }

    LaunchedEffect(uiState.account) {
        uiState.account?.let { account ->
            viewModel.onCollectPackagesBasedOnSignedAccount()
            viewModel.onCollectNotificationsCount(account.id)
        }
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
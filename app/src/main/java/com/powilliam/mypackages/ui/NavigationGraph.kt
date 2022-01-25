package com.powilliam.mypackages.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.powilliam.mypackages.ui.screens.PackageScreen
import com.powilliam.mypackages.ui.screens.AddPackageScreen
import com.powilliam.mypackages.ui.screens.EditPackageScreen
import com.powilliam.mypackages.ui.screens.PackagesMapScreen
import com.powilliam.mypackages.ui.screens.SearchPackageScreen
import com.powilliam.mypackages.ui.viewmodels.PackagesMapUiState
import com.powilliam.mypackages.ui.viewmodels.PackagesMapViewModel

@Composable
fun NavigationGraph(beginSignIn: suspend () -> IntentSenderRequest) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.PackagesMap.route) {
        addPackagesScreen(navController) { beginSignIn() }
    }
}

private fun NavGraphBuilder.addPackagesScreen(
    navController: NavController,
    beginSignIn: suspend () -> IntentSenderRequest
) {
    composable(route = Destination.PackagesMap.route) {
        val context = LocalContext.current

        val viewModel = hiltViewModel<PackagesMapViewModel>()
        val uiState by viewModel.uiState.collectAsState(PackagesMapUiState())
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                val credentials = Identity
                    .getSignInClient(context)
                    .getSignInCredentialFromIntent(result.data)
                viewModel.onAuthenticateWithGoogleSignIn(credentials.googleIdToken)
            } catch (_: Exception) {
            }
        }

        if (uiState.canBeginSignIn) {
            LaunchedEffect(Unit) {
                try {
                    launcher.launch(beginSignIn())
                } catch (_: Exception) {
                }
            }
        }

        PackagesMapScreen(
            uiState = uiState,
            onNavigateToSearchPackageScreen = { navController.navigate(Destination.SearchPackage.route) },
            onNavigateToAddPackageScreen = { navController.navigate(Destination.AddPackage.route) }
        )
    }
    composable(route = Destination.Package.route) {
        PackageScreen()
    }
    composable(route = Destination.AddPackage.route) {
        AddPackageScreen()
    }
    composable(route = Destination.EditPackage.route) {
        EditPackageScreen()
    }
    composable(route = Destination.SearchPackage.route) {
        SearchPackageScreen()
    }
}
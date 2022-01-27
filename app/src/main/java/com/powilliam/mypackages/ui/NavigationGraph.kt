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
import com.powilliam.mypackages.ui.viewmodels.*

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
            onNavigateToAddPackageScreen = { navController.navigate(Destination.AddPackage.route) },
            onNavigateToPackageScreen = {
                navController.navigate(
                    // TODO: Create an method to replace it
                    Destination.Package.route.replace(
                        "{packageId}",
                        "0"
                    )
                )
            }
        )
    }
    composable(route = Destination.Package.route) {
        PackageScreen(
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToEditPackageScreen = { navController.navigate(Destination.EditPackage.route) },
            onDeletePackage = {},
            onMarkPackageAsReceived = {}
        )
    }
    composable(route = Destination.AddPackage.route) {
        val viewModel = hiltViewModel<AddPackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(AddPackageUiState())

        AddPackageScreen(
            uiState = uiState,
            onChangePackageName = { newValue ->
                viewModel.onChangeFormFieldValue(
                    FormField.PackageName,
                    newValue
                )
            },
            onChangePackageTracker = { newValue ->
                viewModel.onChangeFormFieldValue(
                    FormField.PackageTracker,
                    newValue
                )
            },
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onSubmit = { navController.popBackStack() }
        )
    }
    composable(route = Destination.EditPackage.route) {
        val viewModel = hiltViewModel<EditPackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(EditPackageUiState())

        EditPackageScreen(
            uiState = uiState,
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onChangePackageName = viewModel::onChangePackageName,
            onSubmit = { navController.popBackStack() }
        )
    }
    composable(route = Destination.SearchPackage.route) {
        val viewModel = hiltViewModel<SearchPackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(SearchPackageUiState())

        SearchPackageScreen(
            uiState = uiState,
            onSearch = viewModel::onSearch,
            onNavigateToPreviousScreen = { navController.popBackStack() }
        )
    }
}
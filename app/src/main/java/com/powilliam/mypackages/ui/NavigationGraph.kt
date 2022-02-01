package com.powilliam.mypackages.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import com.powilliam.mypackages.ui.screens.PackageScreen
import com.powilliam.mypackages.ui.screens.AddPackageScreen
import com.powilliam.mypackages.ui.screens.EditPackageScreen
import com.powilliam.mypackages.ui.screens.PackagesMapScreen
import com.powilliam.mypackages.ui.screens.SearchPackageScreen
import com.powilliam.mypackages.ui.viewmodels.*
import kotlinx.coroutines.launch

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
        val coroutineScope = rememberCoroutineScope()
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
        val launchSignIn = {
            coroutineScope.launch {
                try {
                    launcher.launch(beginSignIn())
                } catch (e: Exception) {
                }
            }
        }

        PackagesMapScreen(
            uiState = uiState,
            onChangeAccount = { launchSignIn() },
            onSignOut = { viewModel.onSignOut() },
            onFocusAtOnePackage = viewModel::onFocusAtOnePackage,
            onNavigateToSearchPackageScreen = {
                if (uiState.shouldPromptSignIn) {
                    launchSignIn()
                } else {
                    navController.navigate(
                        Destination.SearchPackage.route
                    )
                }
            },
            onNavigateToAddPackageScreen = {
                if (uiState.shouldPromptSignIn) {
                    launchSignIn()
                } else {
                    navController.navigate(Destination.AddPackage.route)
                }
            },
            onNavigateToPackageScreen = {
                if (uiState.shouldPromptSignIn) {
                    launchSignIn()
                } else {
                    navController.navigate(
                        // TODO: Create an method to replace it
                        Destination.Package.route.replace(
                            "{tracker}",
                            it.tracker
                        )
                    )
                }
            }
        )
    }
    composable(
        route = Destination.Package.route,
        arguments = listOf(
            navArgument("tracker") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val viewModel = hiltViewModel<PackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(PackageUiState())

        navBackStackEntry.arguments?.getString("tracker")?.let { tracker ->
            LaunchedEffect(Unit) {
                viewModel.onPopulateScreen(tracker)
            }
        }

        PackageScreen(
            uiState = uiState,
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToEditPackageScreen = {
                uiState.entity?.let { entity ->
                    navController.navigate(
                        Destination.EditPackage.route
                            .replace("{tracker}", entity.tracker)
                            .replace("{name}", entity.name)
                    )
                }
            },
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
            onSubmit = {
                viewModel.onSubmit()
                navController.popBackStack()
            }
        )
    }
    composable(
        route = Destination.EditPackage.route,
        arguments = listOf(
            navArgument("tracker") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val viewModel = hiltViewModel<EditPackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(EditPackageUiState())

        val name = navBackStackEntry.arguments?.getString("name")
        val tracker = navBackStackEntry.arguments?.getString("tracker")

        if (listOf(name, tracker).all { it != null }) {
            LaunchedEffect(Unit) {
                viewModel.onPopulateScreen(name!!, tracker!!)
            }
        }

        EditPackageScreen(
            uiState = uiState,
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onChangePackageName = viewModel::onChangePackageName,
            onSubmit = {
                viewModel.onSubmit()
                navController.popBackStack()
            }
        )
    }
    composable(route = Destination.SearchPackage.route) {
        val viewModel = hiltViewModel<SearchPackageViewModel>()
        val uiState by viewModel.uiState.collectAsState(SearchPackageUiState())

        SearchPackageScreen(
            uiState = uiState,
            onSearch = viewModel::onSearch,
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToPackageScreen = {
                navController.navigate(
                    Destination.Package.route.replace(
                        "{tracker}",
                        it.tracker
                    )
                ) {
                    popUpTo(Destination.PackagesMap.route)
                }
            }
        )
    }
}
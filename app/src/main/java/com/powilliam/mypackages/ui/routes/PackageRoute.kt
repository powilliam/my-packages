package com.powilliam.mypackages.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.powilliam.mypackages.ui.Destination
import com.powilliam.mypackages.ui.screens.PackageScreen
import com.powilliam.mypackages.ui.viewmodels.PackageUiState
import com.powilliam.mypackages.ui.viewmodels.PackageViewModel

@Composable
fun PackageRoute(
    navController: () -> NavController,
    navBackStackEntry: NavBackStackEntry,
) {
    val viewModel = hiltViewModel<PackageViewModel>()
    val uiState by viewModel.uiState.collectAsState(PackageUiState())

    navBackStackEntry.arguments?.getString("tracker")?.let { tracker ->
        LaunchedEffect(Unit) {
            viewModel.onPopulateScreen(tracker)
        }
    }

    PackageScreen(
        uiState = uiState,
        onNavigateToPreviousScreen = { navController().popBackStack() },
        onNavigateToEditPackageScreen = {
            uiState.entity?.let { entity ->
                navController().navigate(
                    Destination.EditPackage.route
                        .replace("{tracker}", entity.tracker)
                        .replace("{name}", entity.name)
                )
            }
        },
        onDeletePackage = {
            viewModel.onDeletePackage()
            navController().popBackStack()
        }
    )
}
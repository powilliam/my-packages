package com.powilliam.mypackages.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.powilliam.mypackages.ui.Destination
import com.powilliam.mypackages.ui.screens.SearchPackageScreen
import com.powilliam.mypackages.ui.viewmodels.SearchPackageUiState
import com.powilliam.mypackages.ui.viewmodels.SearchPackageViewModel

@Composable
fun SearchPackageRoute(navController: () -> NavController) {
    val viewModel = hiltViewModel<SearchPackageViewModel>()
    val uiState by viewModel.uiState.collectAsState(SearchPackageUiState())

    DisposableEffect(Unit) {
        val job = viewModel.onCollectPackagesBasedOnSignedAccount()
        onDispose { job.cancel() }
    }

    SearchPackageScreen(
        uiState = uiState,
        onSearch = viewModel::onSearch,
        onNavigateToPreviousScreen = { navController().popBackStack() },
        onNavigateToPackageScreen = {
            navController().navigate(
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
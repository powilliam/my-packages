package com.powilliam.mypackages.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.powilliam.mypackages.ui.screens.EditPackageScreen
import com.powilliam.mypackages.ui.viewmodels.EditPackageUiState
import com.powilliam.mypackages.ui.viewmodels.EditPackageViewModel

@Composable
fun EditPackageRoute(
    navController: () -> NavController,
    navBackStackEntry: NavBackStackEntry,
) {
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
        onNavigateToPreviousScreen = { navController().popBackStack() },
        onChangePackageName = viewModel::onChangePackageName,
        onSubmit = {
            viewModel.onSubmit()
            navController().popBackStack()
        }
    )
}
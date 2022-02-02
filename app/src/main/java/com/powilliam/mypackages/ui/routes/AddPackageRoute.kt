package com.powilliam.mypackages.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.powilliam.mypackages.ui.screens.AddPackageScreen
import com.powilliam.mypackages.ui.viewmodels.AddPackageUiState
import com.powilliam.mypackages.ui.viewmodels.AddPackageViewModel
import com.powilliam.mypackages.ui.viewmodels.FormField

@Composable
fun AddPackageRoute(navController: () -> NavController) {
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
        onNavigateToPreviousScreen = { navController().popBackStack() },
        onSubmit = {
            viewModel.onSubmit()
            navController().popBackStack()
        }
    )
}
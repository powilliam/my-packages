package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.ui.composables.FormTextField
import com.powilliam.mypackages.ui.viewmodels.EditPackageUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPackageScreen(
    uiState: EditPackageUiState = EditPackageUiState(),
    onNavigateToPreviousScreen: () -> Unit,
    onSubmit: () -> Unit,
    onChangePackageName: (String) -> Unit
) {
    Scaffold(
        topBar = {
            EditPackageScreenAppBar(
                canSubmit = uiState.canSubmit,
                onNavigateToPreviousScreen = onNavigateToPreviousScreen,
                onSubmit = onSubmit
            ) {
                Text(text = "12412412125124")
            }
        }
    ) {
        Column {
            FormTextField(
                value = uiState.packageName,
                placeholder = "Adicione um nome para o pacote",
                label = "Nome do pacote",
                onValueChange = onChangePackageName
            )
        }
    }
}

@Composable
private fun EditPackageScreenAppBar(
    modifier: Modifier = Modifier,
    canSubmit: Boolean = false,
    onNavigateToPreviousScreen: () -> Unit,
    onSubmit: () -> Unit,
    tracker: @Composable () -> Unit,
) {
    LargeTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = { tracker() },
        actions = {
            Button(onClick = { if (canSubmit) onSubmit() }, enabled = canSubmit) {
                Text(text = "Feito")
            }
        }
    )
}
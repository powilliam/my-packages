package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.ui.composables.FormTextField
import com.powilliam.mypackages.ui.validators.ValidationResult
import com.powilliam.mypackages.ui.viewmodels.AddPackageUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPackageScreen(
    uiState: AddPackageUiState = AddPackageUiState(),
    onChangePackageName: (String) -> Unit,
    onChangePackageTracker: (String) -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onSubmit: () -> Unit
) {
    Scaffold(
        topBar = {
            AddPackageScreenAppBar(uiState.canSubmit, onNavigateToPreviousScreen, onSubmit)
        }
    ) {
        Column {
            FormTextField(
                value = uiState.packageName,
                placeholder = "Adicionar um nome para o pacote",
                label = "Nome do pacote",
                onValueChange = onChangePackageName,
                error = if (uiState.hasValidName is ValidationResult.InValid) {
                    { Text(text = uiState.hasValidName.reason) }
                } else null
            )
            FormTextField(
                value = uiState.packageTracker,
                placeholder = "Adicionar o código de rastreio",
                label = "Código de rastreio",
                onValueChange = onChangePackageTracker,
                icon = {
                    Icon(imageVector = Icons.Rounded.Tag, contentDescription = null)
                },
                error = if (uiState.hasValidTracker is ValidationResult.InValid) {
                    { Text(text = uiState.hasValidTracker.reason) }
                } else null
            )
        }
    }
}

@Composable
private fun AddPackageScreenAppBar(
    canSubmit: Boolean = false,
    onNavigateToPreviousScreen: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LargeTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text(text = "Adicionar um pacote")
        },
        actions = {
            Button(onClick = { if (canSubmit) onSubmit() }, enabled = canSubmit) {
                Text(text = "Feito")
            }
        }
    )
}
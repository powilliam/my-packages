package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.powilliam.mypackages.ui.composables.AppBarTextField
import com.powilliam.mypackages.ui.composables.Package
import com.powilliam.mypackages.ui.viewmodels.SearchPackageUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPackageScreen(
    uiState: SearchPackageUiState = SearchPackageUiState(),
    onSearch: (String) -> Unit,
    onNavigateToPreviousScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchPackageScreenAppBar(
                value = uiState.query,
                onValueChange = onSearch,
                onNavigateToPreviousScreen = onNavigateToPreviousScreen
            )
        },
    ) {
        // TODO: Show all packages inside one LazyColumn
        Column {
            Package(tracker = { Text(text = "1234567676") }) {
                Text(text = "Meu pacote")
            }
        }
    }
}

@Composable
private fun SearchPackageScreenAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    onNavigateToPreviousScreen: () -> Unit
) {
    Column {
        AppBarTextField(
            value = value,
            placeholder = "Buscar por nome ou código de rastreio",
            onValueChange = onValueChange,
            navigationIcon = {
                IconButton(onClick = onNavigateToPreviousScreen) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
            }
        )
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
    }
}


package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.powilliam.mypackages.data.entity.Package
import com.powilliam.mypackages.ui.composables.AppBarTextField
import com.powilliam.mypackages.ui.composables.Package
import com.powilliam.mypackages.ui.viewmodels.SearchPackageUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPackageScreen(
    uiState: SearchPackageUiState = SearchPackageUiState(),
    onSearch: (String) -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToPackageScreen: (Package) -> Unit
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
        LazyColumn {
            items(uiState.packages, key = { it.tracker }) { entity ->
                Package(
                    tracker = { Text(text = entity.tracker) },
                    onClick = { onNavigateToPackageScreen(entity) }
                ) {
                    Text(text = entity.name)
                }
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


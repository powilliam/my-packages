package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.ui.composables.EventsCard
import com.powilliam.mypackages.ui.composables.PackageCard
import com.powilliam.mypackages.ui.viewmodels.PackageUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageScreen(
    modifier: Modifier = Modifier,
    uiState: PackageUiState = PackageUiState(),
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToEditPackageScreen: () -> Unit,
    onMarkPackageAsReceived: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            PackageScreenAppBar(
                onNavigateToPreviousScreen,
                onNavigateToEditPackageScreen,
            )
        },
        floatingActionButton = {
            Button(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                onClick = onMarkPackageAsReceived
            ) {
                Text(text = "Recebi esse pacote")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier
                .scrollable(scrollState, orientation = Orientation.Vertical)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.entity?.let { entity ->
                PackageCard(entity)
                EventsCard(events = entity.events)
            }
        }
    }
}

@Composable
private fun PackageScreenAppBar(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToEditPackageScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onNavigateToEditPackageScreen) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
            }
        }
    )
}
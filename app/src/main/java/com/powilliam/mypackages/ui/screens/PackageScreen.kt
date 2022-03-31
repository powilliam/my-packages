package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.ui.composables.ModalBottomSheet
import com.powilliam.mypackages.ui.viewmodels.PackageUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PackageScreen(
    modifier: Modifier = Modifier,
    uiState: PackageUiState = PackageUiState(),
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToEditPackageScreen: () -> Unit,
    onDeletePackage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheet(
        state = bottomSheetState,
        sheetContent = {
            BottomSheetContent(
                onNavigateToEditPackageScreen = {
                    coroutineScope.launch {
                        onNavigateToEditPackageScreen()
                        bottomSheetState.hide()
                    }
                },
                onDeletePackage = {
                    coroutineScope.launch {
                        onDeletePackage()
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        Scaffold(
            modifier = modifier
                .scrollable(scrollState, orientation = Orientation.Vertical)
                .padding(16.dp),
            topBar = {
                PackageScreenAppBar(
                    onNavigateToPreviousScreen = onNavigateToPreviousScreen,
                    onShowBottomSheet = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                ) {
                    uiState.entity?.let { entity ->
                        Text(text = entity.tracker)
                    }
                }
            },
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                uiState.entity?.let { entity ->
                    Box {}
                }
            }
        }
    }
}

@Composable
private fun PackageScreenAppBar(
    modifier: Modifier = Modifier,
    onNavigateToPreviousScreen: () -> Unit,
    onShowBottomSheet: () -> Unit,
    title: @Composable () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = { title() },
        actions = {
            IconButton(onClick = onShowBottomSheet) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
            }
        }
    )
}

@Composable
private fun ColumnScope.BottomSheetContent(
    modifier: Modifier = Modifier,
    onNavigateToEditPackageScreen: () -> Unit,
    onDeletePackage: () -> Unit
) {
    TextButton(onClick = onNavigateToEditPackageScreen) {
        Icon(
            imageVector = Icons.Rounded.Edit,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Editar")
    }
    TextButton(onClick = onDeletePackage) {
        Icon(
            imageVector = Icons.Rounded.DeleteForever,
            contentDescription = null,
            modifier = modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Deletar permanentemente")
    }
}
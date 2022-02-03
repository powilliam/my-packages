package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.data.entity.Notification
import com.powilliam.mypackages.ui.composables.ModalBottomSheet
import com.powilliam.mypackages.ui.composables.NotificationCard
import com.powilliam.mypackages.ui.viewmodels.NotificationsUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState = NotificationsUiState(),
    onDeleteAllNotifications: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheet(
        state = sheetState,
        sheetContent = {
            SheetContent {
                coroutineScope.launch {
                    onDeleteAllNotifications()
                    sheetState.hide()
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                NotificationsScreenAppBar(
                    onShowBottomSheet = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    onNavigateToPreviousScreen = onNavigateToPreviousScreen,
                )
            }
        ) {
            NotificationsList(uiState.notifications)
        }
    }
}

@Composable
private fun NotificationsScreenAppBar(
    modifier: Modifier = Modifier,
    onShowBottomSheet: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = { Text(text = "Notificações") },
        actions = {
            IconButton(onClick = onShowBottomSheet) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NotificationsList(notifications: List<Notification> = emptyList()) {
    LazyColumn {
        items(notifications.reversed(), key = { it.id }) { notification ->
            NotificationCard(
                title = {
                    Text(text = notification.title)
                }
            ) {
                Text(text = notification.body)
            }
        }
    }
}

@Composable
private fun SheetContent(
    onDeleteAllNotifications: () -> Unit,
) {
    TextButton(onClick = onDeleteAllNotifications) {
        Text(text = "Limpar todas as notificações")
    }
}
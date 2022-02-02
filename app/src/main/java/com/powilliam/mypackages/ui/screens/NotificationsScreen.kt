package com.powilliam.mypackages.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.powilliam.mypackages.data.entity.Notification
import com.powilliam.mypackages.ui.composables.NotificationCard
import com.powilliam.mypackages.ui.viewmodels.NotificationsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState = NotificationsUiState(),
    onNavigateToPreviousScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            NotificationsScreenAppBar(onNavigateToPreviousScreen = onNavigateToPreviousScreen)
        }
    ) {
        NotificationsList(uiState.notifications)
    }
}

@Composable
private fun NotificationsScreenAppBar(
    modifier: Modifier = Modifier,
    onNavigateToPreviousScreen: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        title = { Text(text = "Notificações") }
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
package com.powilliam.mypackages.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.powilliam.mypackages.ui.screens.NotificationsScreen
import com.powilliam.mypackages.ui.viewmodels.NotificationsUiState
import com.powilliam.mypackages.ui.viewmodels.NotificationsViewModel
import kotlinx.coroutines.withTimeout

private const val HALF_OF_ONE_SECOND_IN_MILLISECONDS = 500L

@Composable
fun NotificationsRoute(navController: () -> NavController) {
    val viewModel = hiltViewModel<NotificationsViewModel>()
    val uiState by viewModel.uiState.collectAsState(NotificationsUiState())

    LaunchedEffect(uiState.account) {
        uiState.account?.let { account ->
            viewModel.onCollectNotificationsByReceiverId(account.id)
        }
    }

    LaunchedEffect(Unit) {
        withTimeout(HALF_OF_ONE_SECOND_IN_MILLISECONDS) {
            viewModel.onMarkAllNotificationsAsVisualized()
        }
    }

    NotificationsScreen(
        uiState = uiState,
        onNavigateToPreviousScreen = { navController().popBackStack() }
    )
}
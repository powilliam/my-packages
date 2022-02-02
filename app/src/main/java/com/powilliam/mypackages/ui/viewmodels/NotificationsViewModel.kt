package com.powilliam.mypackages.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powilliam.mypackages.data.entity.Notification
import com.powilliam.mypackages.data.entity.UserEntity
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationsUiState(
    val account: UserEntity? = null,
    val notifications: List<Notification> = emptyList()
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<NotificationsUiState> = MutableStateFlow(
        NotificationsUiState()
    )
    val uiState: SharedFlow<NotificationsUiState> =
        _uiState.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        viewModelScope.launch {
            authRepository.getAuthenticatedAccount()
                .distinctUntilChanged { old, new -> old?.id == new?.id }
                .collectLatest { account ->
                    _uiState.update { it.copy(account = account) }
                }
        }
    }

    fun onCollectNotificationsByReceiverId(receiverId: String) = viewModelScope.launch {
        notificationRepository.loadNotificationsByReceiverId(receiverId)
            .collect { notifications ->
                _uiState.update { it.copy(notifications = notifications) }
            }
    }

    fun onMarkAllNotificationsAsVisualized() = viewModelScope.launch {
        notificationRepository.markAllNotificationsAsVisualized()
    }
}
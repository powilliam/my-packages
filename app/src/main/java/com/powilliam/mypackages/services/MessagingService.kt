package com.powilliam.mypackages.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.powilliam.mypackages.data.datasource.NotificationTaskDataSource
import com.powilliam.mypackages.data.repository.AuthRepository
import com.powilliam.mypackages.data.repository.UserSettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var userSettingsRepository: UserSettingsRepository

    @Inject
    lateinit var notificationTaskDataSource: NotificationTaskDataSource

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    override fun onNewToken(token: String) {
        coroutineScope.launch {
            val account = authRepository.getAuthenticatedAccount().firstOrNull() ?: return@launch
            userSettingsRepository.updateDeviceTokenSettings(account.id)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            try {
                notificationTaskDataSource.insertNotification(
                    message.data["uid"] as String,
                    message.data["title"] as String,
                    message.data["body"] as String
                )
            } catch (_: Exception) {
            }
        }
    }
}
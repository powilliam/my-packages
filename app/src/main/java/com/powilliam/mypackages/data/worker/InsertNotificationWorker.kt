package com.powilliam.mypackages.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.powilliam.mypackages.data.entity.Notification
import com.powilliam.mypackages.data.repository.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class InsertNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val receiverId = inputData.getString("receiverId")
                val title = inputData.getString("title")
                val body = inputData.getString("body")

                val notification = Notification(receiverId = receiverId!!, title = title!!, body = body!!)
                notificationRepository.insertNotification(notification)

                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}
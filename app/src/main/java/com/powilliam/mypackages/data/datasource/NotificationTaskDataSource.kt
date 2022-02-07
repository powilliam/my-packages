package com.powilliam.mypackages.data.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.powilliam.mypackages.data.worker.InsertNotificationWorker
import javax.inject.Inject

interface NotificationTaskDataSource {
    fun insertNotification(receiverId: String, title: String, body: String)
}

class NotificationTaskDataSourceImpl @Inject constructor(
    private val workManager: WorkManager
) : NotificationTaskDataSource {
    override fun insertNotification(receiverId: String, title: String, body: String) {
        val data = workDataOf("receiverId" to receiverId, "title" to title, "body" to body)
        val request = OneTimeWorkRequestBuilder<InsertNotificationWorker>()
            .setInputData(data)
            .build()
        workManager.enqueue(request)
    }
}
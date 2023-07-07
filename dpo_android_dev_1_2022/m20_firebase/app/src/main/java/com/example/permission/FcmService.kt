package com.example.permission

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.permission.ui.PhotoListFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notification_important_24)
            .setContentTitle(message.data["nickname"])
            .setContentText(message.data["message"] + convertToDate(message.data["timestamp"]))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()


        NotificationManagerCompat.from(this).notify(PhotoListFragment.NOTIFICATION_ID, notification)
    }

    private fun convertToDate(time: String?): String {
        time ?: return ""
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date(time.toLong() * 1000))
    }
}
package com.example.background_works

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    private val notificationProvider = object : NotificationProvider {}

    override fun onReceive(context: Context, intent: Intent) {
        val notificationText = "WAKE UP!"
        notificationProvider.createNotificationChannel(
            context,
            CHANNEL_ID,
            context.getString(R.string.alarm_channel_name),
            context.getString(R.string.alarm_channel_description)
        )
        val notification =
            notificationProvider.createNotification(context, CHANNEL_ID, notificationText)
        val pendingIntent =
            notificationProvider.getPendingIntent(context, AlarmActivity::class.java)
        notification.apply {
            fullScreenIntent = pendingIntent
            category = NotificationCompat.CATEGORY_ALARM

        }
        notificationProvider.startNotification(context, notificationText, notification)

        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val ringtoneSound = RingtoneManager.getRingtone(context, ringtoneUri)
        ringtoneSound.play()
    }

    companion object {
        private const val CHANNEL_ID = "alarmChannelID"
    }
}

package com.kebob.geta.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kebob.geta.R

internal class FirebaseInstanceIDService : FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("Firebase", "FirebaseInstanceIDService : $s")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage != null && remoteMessage.data.isNotEmpty()) {
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onNotifyModern(title, message)
        } else {
            onNotifyLegacy(title, message)
        }
    }

    private fun onNotifyLegacy(title: String?, message: String?) {
        val notificationBuilder = NotificationCompat.Builder(this, "")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onNotifyModern(title: String?, message: String?) {
        val channel = "bob"
        val channelName = "bobTime"
        val notifyChannel = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelMessage = NotificationChannel(
            channel, channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channelMessage.description = "밥줄시간"
        channelMessage.enableLights(true)
        channelMessage.enableVibration(true)
        channelMessage.setShowBadge(false)
        channelMessage.vibrationPattern = longArrayOf(100, 200, 100, 200)
        notifyChannel.createNotificationChannel(channelMessage)

        val notificationBuilder = NotificationCompat.Builder(this, channel)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channel)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build())
    }

    companion object {
        const val NOTIFY_ID = 9999
    }
}
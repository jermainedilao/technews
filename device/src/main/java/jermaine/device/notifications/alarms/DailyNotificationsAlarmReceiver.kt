package jermaine.device.notifications.alarms

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import jermaine.device.R


class DailyNotificationsAlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "DailyNotifAlarmReceiver"
        const val DAILY_NOTIFICATION_ID = 1000
    }

    private fun createNotificationForEarlierDevices(context: Context) {
        Log.d(TAG, "createNotificationForEarlierDevices: ")

        val contentIntent = Intent("ARTICLES_LIST")
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, 0)

        val notificationBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_stat_notify)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            .setContentTitle(context.getString(R.string.daily_notifications_title))
            .setContentText(context.getString(R.string.daily_notifications_message))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.daily_notifications_message))
            )
            .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(DAILY_NOTIFICATION_ID, notificationBuilder.build())
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationForOreo(context: Context) {
        val channelId = "${context.applicationContext.packageName}.CHANNEL_DAILY_NOTIFICATIONS"
        val notificationChannel = NotificationChannel(
            channelId,
            context.getString(R.string.daily_notifications_text),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = context.getString(R.string.daily_notifications_description_text)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)

        val contentIntent = Intent("ARTICLES_LIST")
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, 0)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_stat_notify)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            .setContentTitle(context.getString(R.string.daily_notifications_title))
            .setContentText(context.getString(R.string.daily_notifications_message))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.daily_notifications_message))
            )
            .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(DAILY_NOTIFICATION_ID, notificationBuilder.build())

        Log.d(TAG, "createNotificationForOreo: ")
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            createNotificationForEarlierDevices(context)
        } else {
            createNotificationForOreo(context)
        }
    }
}
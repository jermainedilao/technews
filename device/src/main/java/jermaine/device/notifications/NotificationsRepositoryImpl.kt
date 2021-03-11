package jermaine.device.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.Completable
import jermaine.device.notifications.alarms.DailyNotificationsAlarmReceiver
import jermaine.domain.di.scopes.ApplicationContext
import jermaine.domain.notifications.NotificationsRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationsRepository {
    companion object {
        const val TAG = "NotificationsRepository"
    }

    /**
     * Creates an alarm that notifies the user every morning.
     **/
    override fun createDailyNotifications(): Completable {
        Log.d(TAG, "createDailyNotifications: Created alarm")

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 8)

        val intent = Intent(context, DailyNotificationsAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, 0
        )

        if (System.currentTimeMillis() > calendar.timeInMillis) {
            // If the time has already passed by.
            // Add 1 day to the date so it will trigger on the following day.
            calendar.add(Calendar.DATE, 1)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
                AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )

        return Completable.complete()
    }
}
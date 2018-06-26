package jermaine.device.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import jermaine.device.notifications.NotificationsRepositoryImpl
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase


class AppUpdateReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "AppUpdateReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")

        val compositeDisposable = CompositeDisposable()
        val notificationsRepository = NotificationsRepositoryImpl(context)
        val createDailyNotificationsUseCase = CreateDailyNotificationsUseCase(notificationsRepository)

        val dailyNotifications = createDailyNotificationsUseCase.execute()
                .subscribe({
                    Log.d(TAG, "Successfully created daily notifications.")
                    compositeDisposable.clear()
                }, {
                    Log.e(TAG, "Error on creating daily notifications.", it)
                })
        compositeDisposable.add(dailyNotifications)
    }
}
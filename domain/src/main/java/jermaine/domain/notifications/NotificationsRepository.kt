package jermaine.domain.notifications

import io.reactivex.Completable


interface NotificationsRepository {
    fun createDailyNotifications(): Completable
}
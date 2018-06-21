package jermaine.domain.notifications.interactors

import io.reactivex.Completable
import jermaine.domain.interactortype.CompletableUseCase
import jermaine.domain.notifications.NotificationsRepository


class CreateDailyNotificationsUseCase(private val notificationsRepository: NotificationsRepository) : CompletableUseCase {
    /**
     * Creates a notification that notifies the user every morning.
     **/
    override fun execute(): Completable {
        return notificationsRepository.createDailyNotifications()
    }
}
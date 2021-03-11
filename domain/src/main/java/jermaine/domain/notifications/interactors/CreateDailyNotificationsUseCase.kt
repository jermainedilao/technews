package jermaine.domain.notifications.interactors

import io.reactivex.Completable
import jermaine.domain.interactortype.CompletableUseCase
import jermaine.domain.notifications.NotificationsRepository
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case responsible for creating a notification that notifies the user every morning.
 **/
@Singleton
class CreateDailyNotificationsUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository
) : CompletableUseCase {
    override fun execute(): Completable = notificationsRepository.createDailyNotifications()
}
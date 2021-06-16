package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jermaine.data.articles.ArticlesRepositoryImpl
import jermaine.device.notifications.NotificationsRepositoryImpl
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.notifications.NotificationsRepository

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun providesArticlesRepository(articlesRepository: ArticlesRepositoryImpl): ArticlesRepository = articlesRepository

    @Provides
    fun providesNotificationRepository(notificationsRepository: NotificationsRepositoryImpl): NotificationsRepository = notificationsRepository
}
package jermaine.technews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import jermaine.data.articles.ArticlesRepositoryImpl
import jermaine.data.articles.db.ArticlesDao
import jermaine.data.articles.service.ArticlesService
import jermaine.device.notifications.NotificationsRepositoryImpl
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.notifications.NotificationsRepository
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesArticlesRepository(articlesService: ArticlesService, articlesDao: ArticlesDao): ArticlesRepository =
            ArticlesRepositoryImpl(articlesService, articlesDao)

    @Singleton
    @Provides
    fun providesNotificationRepository(context: Context): NotificationsRepository =
            NotificationsRepositoryImpl(context)
}
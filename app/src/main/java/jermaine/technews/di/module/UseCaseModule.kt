package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.domain.notifications.NotificationsRepository
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase
import javax.inject.Singleton


@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun providesFetchArticlesListUseCase(articlesRepository: ArticlesRepository): FetchArticlesListUseCase =
            FetchArticlesListUseCase(articlesRepository)

    @Singleton
    @Provides
    fun providesSaveArticleUseCase(articlesRepository: ArticlesRepository): BookmarkArticleUseCase =
            BookmarkArticleUseCase(articlesRepository)

    @Singleton
    @Provides
    fun providesFetchBookmarkedArticlesUseCase(articlesRepository: ArticlesRepository): FetchBookmarkedArticleUseCase =
            FetchBookmarkedArticleUseCase(articlesRepository)

    @Singleton
    @Provides
    fun providesRemoveBookmarkedArticleUseCase(articlesRepository: ArticlesRepository): RemoveBookmarkedArticleUseCase =
            RemoveBookmarkedArticleUseCase(articlesRepository)

    @Singleton
    @Provides
    fun providesCreateDailyNotificationUseCase(notificationsRepository: NotificationsRepository): CreateDailyNotificationsUseCase =
            CreateDailyNotificationsUseCase(notificationsRepository)

}
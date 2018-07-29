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


@Module
class UseCaseModule {
    @Provides
    fun providesFetchArticlesListUseCase(articlesRepository: ArticlesRepository): FetchArticlesListUseCase =
            FetchArticlesListUseCase(articlesRepository)

    @Provides
    fun providesSaveArticleUseCase(articlesRepository: ArticlesRepository): BookmarkArticleUseCase =
            BookmarkArticleUseCase(articlesRepository)

    @Provides
    fun providesFetchBookmarkedArticlesUseCase(articlesRepository: ArticlesRepository): FetchBookmarkedArticleUseCase =
            FetchBookmarkedArticleUseCase(articlesRepository)

    @Provides
    fun providesRemoveBookmarkedArticleUseCase(articlesRepository: ArticlesRepository): RemoveBookmarkedArticleUseCase =
            RemoveBookmarkedArticleUseCase(articlesRepository)

    @Provides
    fun providesCreateDailyNotificationUseCase(notificationsRepository: NotificationsRepository): CreateDailyNotificationsUseCase =
            CreateDailyNotificationsUseCase(notificationsRepository)

}
package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase
import jermaine.technews.ui.articles.ArticlesViewModel
import javax.inject.Singleton


@Module
class ViewModelModule {
    @Singleton
    @Provides
    fun providesArticlesViewModel(
            fetchArticlesListUseCase: FetchArticlesListUseCase,
            bookmarkArticleUseCase: BookmarkArticleUseCase,
            fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase,
            removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase,
            createDailyNotificationsUseCase: CreateDailyNotificationsUseCase
    ): ArticlesViewModel = ArticlesViewModel(
            fetchArticlesListUseCase, bookmarkArticleUseCase,
            fetchBookmarkedArticleUseCase, removeBookmarkedArticleUseCase,
            createDailyNotificationsUseCase
    )
}
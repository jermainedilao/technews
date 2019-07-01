package jermaine.technews.ui.articles.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.technews.ui.articles.model.ArticleViewObject
import javax.inject.Inject


class ArticlesDataSourceFactory @Inject constructor(
    private val appContext: Context,
    private val fetchArticlesListUseCase: FetchArticlesListUseCase,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase
): DataSource.Factory<Int, ArticleViewObject>() {
    private var articlesDataSource: ArticlesDataSource =
        ArticlesDataSource(
            appContext,
            fetchArticlesListUseCase,
            fetchBookmarkedArticleUseCase
        )
    val articlesDataSourceLiveData = MutableLiveData<ArticlesDataSource>(articlesDataSource)

    override fun create(): DataSource<Int, ArticleViewObject> {
        if (articlesDataSource.isInvalid) {
            articlesDataSource = ArticlesDataSource(
                appContext,
                fetchArticlesListUseCase,
                fetchBookmarkedArticleUseCase
            )
            articlesDataSourceLiveData.postValue(articlesDataSource)
        }
        return articlesDataSource
    }
}
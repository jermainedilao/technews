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
    val articlesDataSourceLiveData = MutableLiveData<ArticlesDataSource>()
    private lateinit var articlesDataSource: ArticlesDataSource

    override fun create(): DataSource<Int, ArticleViewObject> {
        articlesDataSource = ArticlesDataSource(
            appContext,
            fetchArticlesListUseCase,
            fetchBookmarkedArticleUseCase
        )
        articlesDataSourceLiveData.postValue(articlesDataSource)
        return articlesDataSource
    }
}
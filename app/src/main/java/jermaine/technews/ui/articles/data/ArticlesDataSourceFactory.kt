package jermaine.technews.ui.articles.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.util.ResourceManager
import javax.inject.Inject


class ArticlesDataSourceFactory @Inject constructor(
    private val resourceManager: ResourceManager,
    private val fetchArticlesListUseCase: FetchArticlesListUseCase,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase
) : DataSource.Factory<Int, ArticleViewObject>() {
    private var articlesDataSource: ArticlesDataSource =
        ArticlesDataSource(
            resourceManager,
            fetchArticlesListUseCase,
            fetchBookmarkedArticleUseCase
        )
    val articlesDataSourceLiveData = MutableLiveData(articlesDataSource)

    override fun create(): DataSource<Int, ArticleViewObject> {
        if (articlesDataSource.isInvalid) {
            articlesDataSource = ArticlesDataSource(
                resourceManager,
                fetchArticlesListUseCase,
                fetchBookmarkedArticleUseCase
            )
            articlesDataSourceLiveData.postValue(articlesDataSource)
        }
        return articlesDataSource
    }
}
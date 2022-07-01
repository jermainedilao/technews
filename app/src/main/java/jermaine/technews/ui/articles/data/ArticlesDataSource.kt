package jermaine.technews.ui.articles.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.reactivex.Observable
import io.reactivex.Single
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.util.ViewObjectParser
import jermaine.technews.util.NEWS_API_URL
import jermaine.technews.util.ResourceManager
import jermaine.technews.util.VIEW_TYPE_ATTRIBUTION


class ArticlesDataSource(
    private val resourceManager: ResourceManager,
    private val fetchArticlesListUseCase: FetchArticlesListUseCase,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase
) : PagingSource<Int, ArticleViewObject>() {

    companion object {
        private const val TAG = "ArticlesDataSource"
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleViewObject>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleViewObject> {
        val key = params.key ?: 1
        val articles = fetchArticlesListUseCase.execute(params.key ?: 1)
        return try {
            val page = LoadResult.Page(
                updateBookMarkStatusFromList(
                    articles.map {
                        ViewObjectParser.articleToViewObjectRepresentation(it, resourceManager)
                    }
                ).apply {
                    if (key != 1 && isNotEmpty()) {
                        addAttribution(this)
                    }
                },
                null,
                if (articles.isEmpty()) null else key + 1
            )

            page
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            LoadResult.Error(e)
        }
    }

    /**
     * Updates the bookmark status of articles inside the list.
     *
     * @param list The list to be updated.
     */
    private suspend fun updateBookMarkStatusFromList(list: List<ArticleViewObject>): MutableList<ArticleViewObject> {
        val bookmarks = fetchBookmarkedArticles()
            .map { it.id }
        return list.map { article ->
            article.setBookmarkDetails(bookmarks.contains(article.id))
            article
        }.toMutableList()
    }

    /**
     * Returns list of bookmarked articles.
     */
    private suspend fun fetchBookmarkedArticles(): List<ArticleViewObject> {
        return fetchBookmarkedArticleUseCase.execute(1)
            .map { article ->
                ViewObjectParser.articleToViewObjectRepresentation(
                    article,
                    resourceManager
                )
            }
    }

    /**
     * Adds News API attribution at the beginning of the list.
     */
    private fun addAttribution(list: MutableList<ArticleViewObject>) {
        val item = ArticleViewObject(viewType = VIEW_TYPE_ATTRIBUTION)
        item.url = NEWS_API_URL
        list.add(0, item)
    }
}
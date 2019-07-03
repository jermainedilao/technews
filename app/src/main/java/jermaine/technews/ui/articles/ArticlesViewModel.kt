package jermaine.technews.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase
import jermaine.technews.base.BaseViewModel
import jermaine.technews.ui.articles.data.ArticlesDataSourceFactory
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.model.UIState
import javax.inject.Inject


class ArticlesViewModel @Inject constructor(
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    val articlesDataSourceFactory: ArticlesDataSourceFactory,
    private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase,
    private val createDailyNotificationsUseCase: CreateDailyNotificationsUseCase
) : BaseViewModel() {

    companion object {
        val TAG = "ArticlesViewModel"
    }

    private val pagedListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(2)
            .build()
    }

    val articlesListLiveData: LiveData<PagedList<ArticleViewObject>> by lazy {
        LivePagedListBuilder(articlesDataSourceFactory, pagedListConfig).build()
    }

    val uiState: LiveData<UIState> by lazy {
        Transformations.switchMap(
            articlesDataSourceFactory.articlesDataSourceLiveData
        ) {
            it.uiState
        }
    }

    /**
     * Bookmarks an article.
     **/
    fun bookmarkArticle(article: ArticleViewObject): Completable =
        bookmarkArticleUseCase.execute(article.toDomainRepresentation())

    /**
     * Removes bookmarked article.
     **/
    fun removeBookmarkedArticle(article: ArticleViewObject): Completable =
        removeBookmarkedArticleUseCase.execute(article.toDomainRepresentation())

    /**
     * Creates a daily notification.
     **/
    fun createDailyNotifications(): Completable = createDailyNotificationsUseCase.execute()

    override fun onCleared() {
        articlesDataSourceFactory.articlesDataSourceLiveData.value!!.destroy()
        super.onCleared()
    }
}
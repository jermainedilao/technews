package jermaine.technews.ui.articles

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase
import jermaine.technews.base.BaseViewModel
import jermaine.technews.ui.articles.data.ArticlesDataSource
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.util.PaginationUtils
import jermaine.technews.util.ResourceManager
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase,
    private val createDailyNotificationsUseCase: CreateDailyNotificationsUseCase,
    private val resourceManager: ResourceManager,
    private val fetchArticlesListUseCase: FetchArticlesListUseCase,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase
) : BaseViewModel() {

    companion object {
        const val TAG = "ArticlesViewModel"
    }

    private val pager = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
            enablePlaceholders = false,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            ArticlesDataSource(
                resourceManager = resourceManager,
                fetchArticlesListUseCase = fetchArticlesListUseCase,
                fetchBookmarkedArticleUseCase = fetchBookmarkedArticleUseCase
            )
        }
    )

    val articles = pager.liveData.cachedIn(viewModelScope)

    private val _paginationState by lazy {
        PublishSubject.create<PaginationUtils.PaginationUtilStates>()
    }

    val paginationState: Observable<PaginationUtils.PaginationUtilStates> = _paginationState

    private var isFirstLoad = true

    fun onLoadStateChanged(loadStates: CombinedLoadStates, isListEmpty: Boolean) {
        PaginationUtils.onLoadStateChanged(
            loadStates, isListEmpty, isFirstLoad
        ).apply {
            if (this is PaginationUtils.PaginationUtilStates.ShowLoadingFirstLoad) {
                isFirstLoad = false
            }

            _paginationState.onNext(this)
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
}
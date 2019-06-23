package jermaine.technews.ui.articles

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.domain.notifications.interactors.CreateDailyNotificationsUseCase
import jermaine.technews.ui.articles.data.ArticlesDataSourceFactory
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.util.ViewObjectParser
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesViewModel @Inject constructor(
    private val context: Context,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    val articlesDataSourceFactory: ArticlesDataSourceFactory,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase,
    private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase,
    private val createDailyNotificationsUseCase: CreateDailyNotificationsUseCase
): ViewModel() {

    companion object {
        val TAG = "ArticlesViewModel"
    }

    val compositeDisposable = CompositeDisposable()

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
     * Returns list of bookmarked articles.
     **/
    fun fetchBookmarkedArticles(page: Int): Single<List<ArticleViewObject>> =
        fetchBookmarkedArticleUseCase.execute(page)
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .map {
                ViewObjectParser.articleToViewObjectRepresentation(it, context)
            }
            .toList()

    /**
     * Creates a daily notification.
     **/
    fun createDailyNotifications(): Completable = createDailyNotificationsUseCase.execute()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
package jermaine.technews.ui.articles.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.util.ViewObjectParser
import jermaine.technews.util.VIEW_TYPE_ARTICLE
import jermaine.technews.util.VIEW_TYPE_ATTRIBUTION


class ArticlesDataSource(
    private val appContext: Context,
    private val fetchArticlesListUseCase: FetchArticlesListUseCase,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase
) : PageKeyedDataSource<Int, ArticleViewObject>() {

    companion object {
        const val TAG = "ArticlesDataSource"
    }

    private val compositeDisposable = CompositeDisposable()
    val refreshState = MutableLiveData<Boolean>()
    val paginateState = MutableLiveData<Boolean>()


    /**
     * Fetches initial list of articles.
     *
     * Also, updates bookmark status of the articles inside the list.
     *
     * This is also responsible to trigger refreshState to show or not.
     *
     * @see refreshState
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleViewObject>) {
        val disposable = fetchArticlesListUseCase.execute(1)
            .flatMapObservable {
                Observable.fromIterable(it)  // Convert list to iterable.
            }
            .map {
                ViewObjectParser.articleToViewObjectRepresentation(it, appContext)
            }
            .toList()
            .flatMap { updateBookMarkStatusFromList(it) }
            .doOnSubscribe {
                // Show refreshing in loading initial state
                refreshState.postValue(true)
            }
            .doOnSuccess { refreshState.postValue(false) }
            .doOnError { refreshState.postValue(false) }
            .subscribe({
                callback.onResult(it, 1, 2)
            }, {
                throw it
            })

        compositeDisposable.add(disposable)
    }

    /**
     * Fetches succeeding pages of articles.
     *
     * Also, updates bookmark status of the articles inside the list.
     *
     * Also, adds News API attribution at the beginning of the list in every new page.
     *
     * This is also responsible to trigger paginateState to show or not.
     *
     * @see paginateState
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleViewObject>) {
        val disposable = fetchArticlesListUseCase.execute(params.key)
            .flatMapObservable {
                Observable.fromIterable(it)  // Convert list to iterable.
            }
            .map {
                ViewObjectParser.articleToViewObjectRepresentation(it, appContext)
            }
            .toList()
            .flatMap {
                updateBookMarkStatusFromList(it)
            }
            .map {
                it.apply { addAttribution(it) }
            }
            .doOnSubscribe {
                // Show refreshing in loading initial state
                paginateState.postValue(true)
            }
            .doOnSuccess { paginateState.postValue(false) }
            .doOnError { paginateState.postValue(false) }
            .subscribe({
                callback.onResult(it, params.key + 1)
            }, {
                throw it
            })

        compositeDisposable.add(disposable)
    }

    /**
     * Updates the bookmark status of articles inside the list.
     *
     * @param list The list to be updated.
     */
    private fun updateBookMarkStatusFromList(list: List<ArticleViewObject>): Single<MutableList<ArticleViewObject>> {
        val zipper = BiFunction<
                List<ArticleViewObject>,
                List<ArticleViewObject>,
                Pair<List<ArticleViewObject>, List<ArticleViewObject>>> { t1, t2 -> Pair(t1, t2) }
        return Single.just(list)
            .zipWith(fetchBookmarkedArticles(), zipper)
            .flatMapObservable {
                val articles = it.first
                val bookmarks = it.second
                Observable.fromIterable(articles)
                    .concatMap { article ->
                        Observable.fromIterable(bookmarks)
                            .filter {
                                article.id.contentEquals(it.id)
                            }
                            .first(ArticleViewObject(id = "none", viewType = VIEW_TYPE_ARTICLE))
                            .map {
                                val bookmarked = !it.id.contentEquals("none")

                                article.setBookmarkDetails(bookmarked)

                                article
                            }
                            .toObservable()
                    }
            }
            .toList()
    }

    /**
     * Returns list of bookmarked articles.
     */
    private fun fetchBookmarkedArticles(): SingleSource<List<ArticleViewObject>> {
        return fetchBookmarkedArticleUseCase.execute(1)
            .flatMapObservable { bookmarkedList ->
                Observable.fromIterable(bookmarkedList)
            }
            .map {
                ViewObjectParser.articleToViewObjectRepresentation(
                    it,
                    appContext
                )
            }
            .toList()
    }

    /**
     * Adds News API attribution at the beginning of the list.
     */
    private fun addAttribution(list: MutableList<ArticleViewObject>) {
        val item = ArticleViewObject(viewType = VIEW_TYPE_ATTRIBUTION)
        item.url = "https://newsapi.org"
        list.add(0, item)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleViewObject>) = Unit

    fun destroy() {
        compositeDisposable.clear()
    }
}
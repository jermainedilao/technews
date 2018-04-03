package jermaine.technews.ui.articles

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.interactors.articles.FetchArticlesListUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.BookmarkArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.technews.ui.articles.adapter.ArticlesListAdapter
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.util.ViewObjectParser


class ArticlesViewModel(
        private val fetchArticlesListUseCase: FetchArticlesListUseCase,
        private val bookmarkArticleUseCase: BookmarkArticleUseCase,
        private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase,
        private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase
) {

    companion object {
        val TAG = "ArticlesViewModel"
    }

    /**
     * Show paginate indicator if emitted value is true.
     * If false hide refresh paginate.
     **/
    val paginateIndicator: PublishSubject<Boolean> = PublishSubject.create()

    /**
     * Show refresh indicator if emitted value is true.
     * If false hide refresh indicator.
     **/
    val refreshIndicator: PublishSubject<Boolean> = PublishSubject.create()

    /**
     * Returns list of articles observable.
     * This is also responsible to trigger
     * pageIndicator and refreshIndicator to show or not.
     *
     * @see paginateIndicator
     * @see refreshIndicator
     **/
    fun fetchArticles(page: Int): Single<List<ArticleViewObject>> {
        Log.d(TAG, "fetchArticles: ")
        return fetchArticlesListUseCase.execute(page)
                .flatMapObservable {
                    Observable.fromIterable(it)
                }
                .map {
                    ViewObjectParser.articleToViewObjectRepresentation(it)
                }
                .toList()
                .map {
                    if (page != 1) {
                        addAtrribution(it)
                    }
                    it
                }
                .doOnSubscribe {
                    // Only show refreshing if fetching first page.
                    if (page == 1) {
                        refreshIndicator.onNext(true)
                    } else {
                        paginateIndicator.onNext(true)
                    }
                }
                .doOnSuccess {
                    refreshIndicator.onNext(false)

                    if (page != 1) {
                        paginateIndicator.onNext(false)
                    }
                }
                .doOnError {
                    refreshIndicator.onNext(false)

                    if (page != 1) {
                        paginateIndicator.onNext(false)
                    }
                }
    }

    private fun addAtrribution(list: MutableList<ArticleViewObject>) {
        val item = ArticleViewObject(viewType = ArticlesListAdapter.VIEW_TYPE_ATTRIBUTION)
        item.url = "https://newsapi.org"
        list.add(0, item)
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
                        ViewObjectParser.articleToViewObjectRepresentation(it)
                    }
                    .toList()
}
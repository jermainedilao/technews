package jermaine.technews.ui.articles

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.interactors.FetchArticlesListUseCase
import jermaine.domain.articles.model.Article


class ArticlesViewModel(private val fetchArticlesListUseCase: FetchArticlesListUseCase) {

    companion object {
        val TAG = "ArticlesViewModel"
    }

    val paginateIndicator: PublishSubject<Boolean> = PublishSubject.create<Boolean>()
    val refreshIndicator: PublishSubject<Boolean> = PublishSubject.create<Boolean>()

    fun fetchArticles(page: Int): Observable<List<Article>> {
        Log.d(TAG, "fetchArticles: ")
        return fetchArticlesListUseCase.execute(page)
                .toObservable()
                .doOnSubscribe {
                    // Only show refreshing if fetching first page.
                    if (page == 1) {
                        refreshIndicator.onNext(true)
                    } else {
                        paginateIndicator.onNext(true)
                    }
                }
                .doOnNext {
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
}
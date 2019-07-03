package jermaine.technews.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import jermaine.domain.articles.interactors.articles.bookmarks.FetchBookmarkedArticleUseCase
import jermaine.domain.articles.interactors.articles.bookmarks.RemoveBookmarkedArticleUseCase
import jermaine.technews.R
import jermaine.technews.base.BaseViewModel
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.articles.model.UIState
import jermaine.technews.ui.articles.util.ViewObjectParser
import jermaine.technews.util.ResourceManager
import javax.inject.Inject


class BookmarksViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase,
    private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase
) : BaseViewModel() {

    private val _uiState: MutableLiveData<UIState> by lazy {
        MutableLiveData<UIState>(UIState.Loading)
    }
    val uiState: LiveData<UIState> = _uiState

    /**
     * Returns list of bookmarked articles.
     **/
    fun fetchBookmarkedArticles(page: Int): Single<List<ArticleViewObject>> =
        fetchBookmarkedArticleUseCase.execute(page)
            .doOnSubscribe {
                _uiState.postValue(UIState.Loading)
            }
            .doOnSuccess {
                _uiState.postValue(UIState.HasData)
            }
            .doOnError {
                _uiState.postValue(UIState.Error(R.string.error_text))
            }
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .map {
                ViewObjectParser.articleToViewObjectRepresentation(it, resourceManager)
            }
            .toList()

    /**
     * Removes bookmarked article.
     **/
    fun removeBookmarkedArticle(article: ArticleViewObject): Completable =
        removeBookmarkedArticleUseCase.execute(article.toDomainRepresentation())
}
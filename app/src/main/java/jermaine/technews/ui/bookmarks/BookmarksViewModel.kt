package jermaine.technews.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val resourceManager: ResourceManager,
    private val fetchBookmarkedArticleUseCase: FetchBookmarkedArticleUseCase,
    private val removeBookmarkedArticleUseCase: RemoveBookmarkedArticleUseCase
) : BaseViewModel() {

    private val _uiState: MutableLiveData<UIState> by lazy {
        MutableLiveData<UIState>(UIState.Loading)
    }
    val uiState: LiveData<UIState> = _uiState

    private val _bookmarkedArticles by lazy {
        MutableLiveData<List<ArticleViewObject>>()
    }

    val bookmarkedArticles: LiveData<List<ArticleViewObject>> = _bookmarkedArticles

    /**
     * Returns list of bookmarked articles.
     **/
    fun fetchBookmarkedArticles(page: Int) {
        viewModelScope.launch {
            try {
                _uiState.postValue(UIState.Loading)

                _bookmarkedArticles.value = fetchBookmarkedArticleUseCase
                    .execute(page)
                    .map {
                        ViewObjectParser.articleToViewObjectRepresentation(it, resourceManager)
                    }

                _uiState.postValue(UIState.HasData)
            } catch (e: Exception) {
                _uiState.postValue(UIState.Error(R.string.error_text))
            }

        }
    }

    /**
     * Removes bookmarked article.
     **/
    fun removeBookmarkedArticle(article: ArticleViewObject): Completable =
        removeBookmarkedArticleUseCase.execute(article.toDomainRepresentation())
}
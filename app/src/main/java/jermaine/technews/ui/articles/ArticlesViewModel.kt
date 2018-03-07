package jermaine.technews.ui.articles

import io.reactivex.Single
import jermaine.domain.articles.interactors.FetchArticlesListUseCase
import jermaine.domain.articles.model.Article


class ArticlesViewModel(private val fetchArticlesListUseCase: FetchArticlesListUseCase) {
    fun fetchArticles(): Single<List<Article>> = fetchArticlesListUseCase.execute()
}
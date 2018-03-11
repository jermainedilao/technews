package jermaine.domain.articles

import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesRepository {
    fun fetchArticles(page: Int): Single<List<Article>>
}
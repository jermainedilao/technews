package jermaine.domain.articles

import io.reactivex.Completable
import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesRepository {
    fun fetchArticles(page: Int): Single<List<Article>>

    fun bookMarkArticle(article: Article): Completable

    fun fetchBookMarkedArticles(page: Int): Single<List<Article>>

    fun fetchAllBookMarkedArticles(): Single<List<Article>>
}
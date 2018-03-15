package jermaine.data.articles.db

import io.reactivex.Completable
import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesDao {
    fun fetchBookmarkedArticles(page: Int): Single<List<Article>>

    fun fetchAllBookmarkedArticles(): Single<List<Article>>

    fun bookMarkArticle(article: Article): Completable
}
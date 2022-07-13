package jermaine.data.articles.db

import io.reactivex.Completable
import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesDao {
    suspend fun fetchBookmarkedArticles(page: Int): List<Article>

    fun bookMarkArticle(article: Article): Completable

    fun removeBookmarkedArticle(article: Article): Completable
}
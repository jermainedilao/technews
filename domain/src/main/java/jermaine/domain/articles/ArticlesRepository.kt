package jermaine.domain.articles

import io.reactivex.Completable
import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesRepository {
    /**
     * Returns list of articles from the service.
     *
     * @param page Page to be returned (used for pagination).
     **/
    suspend fun fetchArticles(page: Int): List<Article>

    /**
     * Sets article.bookmark to true.
     **/
    fun bookMarkArticle(article: Article): Completable

    /**
     * Returns list of bookmarked articles.
     **/
    suspend fun fetchBookMarkedArticles(page: Int): List<Article>

    /**
     * Sets article.bookmark to false.
     **/
    fun removeBookmarkedArticle(article: Article): Completable
}
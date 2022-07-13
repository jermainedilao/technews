package jermaine.data.articles

import io.reactivex.Completable
import jermaine.data.articles.db.ArticlesDao
import jermaine.data.articles.service.ArticlesService
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepositoryImpl @Inject constructor(
    private val articlesService: ArticlesService,
    private val articlesDao: ArticlesDao
) : ArticlesRepository {
    override suspend fun fetchArticles(page: Int): List<Article> =
        articlesService.fetchArticles(page)

    override fun bookMarkArticle(article: Article): Completable =
        articlesDao.bookMarkArticle(article)

    override suspend fun fetchBookMarkedArticles(page: Int): List<Article> =
        articlesDao.fetchBookmarkedArticles(page)

    override fun removeBookmarkedArticle(article: Article): Completable =
        articlesDao.removeBookmarkedArticle(article)
}

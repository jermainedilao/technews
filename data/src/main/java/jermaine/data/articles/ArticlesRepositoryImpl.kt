package jermaine.data.articles

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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

    override fun fetchArticles(page: Int): Single<List<Article>> =
            articlesService.fetchArticles(page)
                    .subscribeOn(Schedulers.io())

    override fun bookMarkArticle(article: Article): Completable =
            articlesDao.bookMarkArticle(article)

    override fun fetchBookMarkedArticles(page: Int): Single<List<Article>> =
            articlesDao.fetchBookmarkedArticles(page)

    override fun fetchAllBookMarkedArticles(): Single<List<Article>> =
            articlesDao.fetchAllBookmarkedArticles()

    override fun removeBookmarkedArticle(article: Article): Completable =
            articlesDao.removeBookmarkedArticle(article)
}

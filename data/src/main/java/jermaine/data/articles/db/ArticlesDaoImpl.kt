package jermaine.data.articles.db

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jermaine.data.articles.db.room.ArticleRoomDao
import jermaine.data.articles.db.util.DataObjectParser
import jermaine.domain.articles.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesDaoImpl @Inject constructor(
    private val articleRoomDao: ArticleRoomDao
) : ArticlesDao {
    override suspend fun fetchBookmarkedArticles(page: Int): List<Article> {
        return withContext(Dispatchers.IO) {
            articleRoomDao
                .fetchArticlesCo()
                .map { it.toDomainRepresentation() }
        }
    }


    override fun bookMarkArticle(article: Article): Completable =
        Single.just(article)
            .subscribeOn(Schedulers.io())
            .map {
                val item = DataObjectParser.articleToDataRepresentation(it)
                item.bookmarked = true

                item
            }
            .flatMapCompletable {
                articleRoomDao.saveArticle(it)
                Completable.complete()
            }

    override fun removeBookmarkedArticle(article: Article): Completable =
        Single.just(article)
            .subscribeOn(Schedulers.io())
            .map {
                val item = DataObjectParser.articleToDataRepresentation(it)
                item.bookmarked = true

                item
            }
            .flatMapCompletable {
                articleRoomDao.deleteArticle(it)
                Completable.complete()
            }
}
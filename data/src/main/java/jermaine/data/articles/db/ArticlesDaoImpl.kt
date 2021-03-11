package jermaine.data.articles.db

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jermaine.data.articles.db.room.ArticleRoomDao
import jermaine.data.articles.db.util.DataObjectParser
import jermaine.domain.articles.model.Article
import javax.inject.Inject
import javax.inject.Singleton


@Singleton class ArticlesDaoImpl @Inject constructor(private val articleRoomDao: ArticleRoomDao) : ArticlesDao {
    override fun fetchAllBookmarkedArticles(): Single<List<Article>> =
            articleRoomDao.fetchArticles()
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        Observable.fromIterable(it)
                    }
                    .map {
                        // convert it to DomainRepresentation
                        it.toDomainRepresentation()
                    }
                    .toList()

    override fun fetchBookmarkedArticles(page: Int): Single<List<Article>> =
            articleRoomDao.fetchArticles()
                    .subscribeOn(Schedulers.io())
                    .flatMapObservable {
                        Observable.fromIterable(it)
                    }
                    .map {
                        // convert it to DomainRepresentation
                        it.toDomainRepresentation()
                    }
                    .toList()


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
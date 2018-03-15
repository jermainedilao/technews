package jermaine.domain.articles.interactors.articles

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.articles.util.ArticleUtil
import jermaine.domain.interactortype.SingleWithParamUseCase


/**
 * Use case responsible for fetching list of articles and checking
 * if articles are previously bookmarked already.
 **/
class FetchArticlesListUseCase(private val articlesRepository: ArticlesRepository) : SingleWithParamUseCase<Int, List<Article>> {
    override fun execute(page: Int): Single<List<Article>> {
        return articlesRepository.fetchArticles(page)
                .zipWith(articlesRepository.fetchAllBookMarkedArticles(), BiFunction<List<Article>, List<Article>, Pair<List<Article>, List<Article>>> { t1, t2 -> Pair(t1, t2) })
                .flatMapObservable {
                    val articles = it.first
                    val bookmarks = it.second

                    Observable.fromIterable(articles)
                            .concatMap {
                                val article = it
                                Observable.fromIterable(bookmarks)
                                        .filter {
                                            ArticleUtil.getIdValue(article).contentEquals(it.id)
                                        }
                                        .first(Article(id = "none"))
                                        .map {
                                            article.bookmarked = !it.id.contentEquals("none")
                                            article.bookmarked
                                            article
                                        }
                                        .toObservable()
                            }
                }
                .toList()
    }
}
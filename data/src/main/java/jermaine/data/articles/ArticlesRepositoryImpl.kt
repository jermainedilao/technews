package jermaine.data.articles

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jermaine.data.articles.service.ArticlesService
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article

class ArticlesRepositoryImpl(private val articlesService: ArticlesService): ArticlesRepository {
    /**
     * Returns list of articles from the service.
     *
     * @param page Page to be returned (used for pagination).
     **/
    override fun fetchArticles(page: Int): Single<List<Article>> =
            articlesService.fetchArticles(page)
                    .subscribeOn(Schedulers.io())
}

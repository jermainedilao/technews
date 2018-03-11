package jermaine.data.articles

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jermaine.data.articles.service.ArticlesService
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article

class ArticlesRepositoryImpl(private val articlesService: ArticlesService): ArticlesRepository {
    override fun fetchArticles(page: Int): Single<List<Article>> =
            articlesService.fetchArticles(page)
                    .subscribeOn(Schedulers.io())
}

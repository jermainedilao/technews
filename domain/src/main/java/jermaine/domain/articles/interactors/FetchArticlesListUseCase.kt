package jermaine.domain.articles.interactors

import io.reactivex.Single
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.SingleWithParamUseCase


class FetchArticlesListUseCase(private val articlesRepository: ArticlesRepository): SingleWithParamUseCase<Int, List<Article>> {
    override fun execute(page: Int): Single<List<Article>> = articlesRepository.fetchArticles(page)
}
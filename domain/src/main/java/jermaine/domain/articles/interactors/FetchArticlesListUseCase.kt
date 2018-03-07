package jermaine.domain.articles.interactors

import io.reactivex.Single
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.SingleUseCase


class FetchArticlesListUseCase(private val articlesRepository: ArticlesRepository): SingleUseCase<List<Article>> {
    override fun execute(): Single<List<Article>> = articlesRepository.fetchArticles()
}
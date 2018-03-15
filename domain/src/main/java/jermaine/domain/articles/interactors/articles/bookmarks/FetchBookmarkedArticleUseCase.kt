package jermaine.domain.articles.interactors.articles.bookmarks

import io.reactivex.Single
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.SingleWithParamUseCase


class FetchBookmarkedArticleUseCase(private val articlesRepository: ArticlesRepository) : SingleWithParamUseCase<Int, List<Article>> {
    override fun execute(page: Int): Single<List<Article>> =
            articlesRepository.fetchBookMarkedArticles(page)
}
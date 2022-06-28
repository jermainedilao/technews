package jermaine.domain.articles.interactors.articles

import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.WithParamUseCase
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case responsible for fetching list of articles.
 **/
@Singleton
class FetchArticlesListUseCase @Inject constructor(
        private val articlesRepository: ArticlesRepository
) : WithParamUseCase<Int, List<Article>> {
    override suspend fun execute(page: Int): List<Article> =
            articlesRepository.fetchArticles(page)
}
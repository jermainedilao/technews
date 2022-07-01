package jermaine.domain.articles.interactors.articles.bookmarks

import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.WithParamUseCase
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case responsible for fetching list of bookmarked articles.
 **/
@Singleton
class FetchBookmarkedArticleUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : WithParamUseCase<Int, List<Article>> {
    override suspend fun execute(page: Int): List<Article> =
        articlesRepository.fetchBookMarkedArticles(page)
}
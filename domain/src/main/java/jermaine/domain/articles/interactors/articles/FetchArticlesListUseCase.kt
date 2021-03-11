package jermaine.domain.articles.interactors.articles

import io.reactivex.Single
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.SingleWithParamUseCase
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case responsible for fetching list of articles.
 **/
@Singleton
class FetchArticlesListUseCase @Inject constructor(
        private val articlesRepository: ArticlesRepository
) : SingleWithParamUseCase<Int, List<Article>> {
    override fun execute(page: Int): Single<List<Article>> =
            articlesRepository.fetchArticles(page)
}
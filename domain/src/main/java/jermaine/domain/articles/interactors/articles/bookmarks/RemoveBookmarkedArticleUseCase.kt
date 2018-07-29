package jermaine.domain.articles.interactors.articles.bookmarks

import io.reactivex.Completable
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.CompletableWithParamUseCase
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Use case responsible for unbookmarking article.
 **/
@Singleton
class RemoveBookmarkedArticleUseCase @Inject constructor(
        private val articlesRepository: ArticlesRepository
) : CompletableWithParamUseCase<Article> {
    override fun execute(article: Article): Completable =
            articlesRepository.removeBookmarkedArticle(article)
}
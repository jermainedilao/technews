package jermaine.domain.articles.interactors.articles.bookmarks

import io.reactivex.Completable
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.model.Article
import jermaine.domain.interactortype.CompletableUseCaseWithParam


class BookmarkArticleUseCase(private val articlesRepository: ArticlesRepository) : CompletableUseCaseWithParam<Article> {
    override fun execute(article: Article): Completable =
            articlesRepository.bookMarkArticle(article)
}
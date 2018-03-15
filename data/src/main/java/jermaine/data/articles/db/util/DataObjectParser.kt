package jermaine.data.articles.db.util

import jermaine.data.articles.db.model.ArticleModel
import jermaine.domain.articles.util.ArticleUtil


object DataObjectParser {
    fun articleToDataRepresentation(article: jermaine.domain.articles.model.Article): ArticleModel =
            with(article) {
                ArticleModel(
                        id = ArticleUtil.getIdValue(article),
                        source = source,
                        author = author,
                        title = title,
                        description = description,
                        url = url,
                        urlToImage = urlToImage,
                        publishedAt = publishedAt,
                        bookmarked = bookmarked
                )
            }
}
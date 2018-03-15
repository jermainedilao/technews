package jermaine.technews.ui.articles.util

import jermaine.domain.articles.util.ArticleUtil
import jermaine.technews.R
import jermaine.technews.ui.articles.model.ArticleViewObject


object ViewObjectParser {
    fun articleToViewRepresentation(article: jermaine.domain.articles.model.Article): ArticleViewObject =
            with(article) {
                ArticleViewObject(
                        id = ArticleUtil.getIdValue(article),
                        source = source,
                        author = author,
                        title = title,
                        description = description,
                        url = url,
                        urlToImage = urlToImage,
                        publishedAt = publishedAt,
                        bookmarked = bookmarked,
                        bookmarkDrawableResId = if (bookmarked) R.drawable.ic_bookmark_red_24dp else R.drawable.ic_bookmark_border_red_24dp
                )
            }
}
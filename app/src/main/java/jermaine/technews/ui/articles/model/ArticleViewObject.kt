package jermaine.technews.ui.articles.model

import jermaine.domain.articles.model.Article
import jermaine.domain.articles.model.Source
import jermaine.technews.R


class ArticleViewObject(
        var id: String = "",
        var source: Source = Source(),
        var author: String = "",
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var urlToImage: String = "",
        var publishedAt: String = "",
        var bookmarked: Boolean = false,
        var bookmarkDrawableResId: Int,
        var bookmarkButtonTextResId: Int = R.string.add_text
) {
    fun toDomainRepresentation(): Article = Article(
            id = id,
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
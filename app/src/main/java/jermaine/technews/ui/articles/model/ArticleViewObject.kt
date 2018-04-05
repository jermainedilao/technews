package jermaine.technews.ui.articles.model

import jermaine.domain.articles.model.Article
import jermaine.domain.articles.model.Source
import jermaine.technews.R


data class ArticleViewObject(
        var id: String = "",
        var source: Source = Source(),
        var author: String = "",
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var urlToImage: String = "",
        var publishedAt: String = "",
        var bookmarked: Boolean = false,
        var bookmarkDrawableResId: Int = 0,
        var bookmarkButtonTextResId: Int = R.string.add_text,
        var containerAlpha: Float = DEFAULT_STATE,
        var viewType: Int
) {
    companion object {
        const val DEFAULT_STATE = 1.0F
        const val LOADING_STATE = 0.6F
    }

    /**
     * Updates bookmark status and bookmark view details.
     **/
    fun setBookmarkDetails(bookmarked: Boolean) {
        this.bookmarked = bookmarked
        bookmarkDrawableResId = if (bookmarked) R.drawable.ic_bookmark_red_24dp else R.drawable.ic_bookmark_border_red_24dp
        bookmarkButtonTextResId = if (bookmarked) R.string.remove_text else R.string.add_text
    }

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
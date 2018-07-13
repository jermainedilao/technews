package jermaine.technews.ui.articles.util

import jermaine.domain.articles.util.ArticleUtil
import jermaine.technews.R
import jermaine.technews.ui.articles.adapter.ArticlesListAdapter
import jermaine.technews.ui.articles.model.ArticleViewObject
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.concurrent.TimeUnit


object ViewObjectParser {
    fun articleToViewObjectRepresentation(article: jermaine.domain.articles.model.Article): ArticleViewObject =
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
                        publishedAtDisplay = getPublishedAtDisplay(publishedAt),
                        bookmarked = bookmarked,
                        bookmarkDrawableResId = if (bookmarked) R.drawable.ic_bookmark_blue_24dp else R.drawable.ic_bookmark_border_gray_24dp,
                        bookmarkButtonTextResId = R.string.bookmark_text,
                        bookmarkButtonTextColorResId = if (bookmarked) R.color.blue else R.color.light_gray,
                        viewType = ArticlesListAdapter.VIEW_TYPE_ARTICLE
                )
            }

    /**
     * Returns elapsed time since publishedAt.
     *
     * @param publishedAt Date string of date published. Must be in this UTC format (2011-12-03T10:15:30Z).
     **/
    private fun getPublishedAtDisplay(publishedAt: String): String {
        val publishedAtInstant = Instant.parse(publishedAt)
        val zonedPublishedAt = publishedAtInstant.atZone(ZoneId.systemDefault()) // Converts the UTC datetime string to local time.
        val now = ZonedDateTime.now()

        val elapsedTime = now.toEpochSecond() - zonedPublishedAt.toEpochSecond()

        val days = TimeUnit.SECONDS.toDays(elapsedTime)
        val hours = TimeUnit.SECONDS.toHours(elapsedTime)
        val minutes = TimeUnit.SECONDS.toMinutes(elapsedTime)

        return when {
            days > 1 -> "$days days ago"
            days > 0 -> "$days day ago"
            hours > 1 -> "$hours hours ago"
            hours > 0 -> "$hours hour ago"
            minutes > 1 -> "$minutes minutes ago"
            minutes > 0 -> "$minutes minute ago"
            else -> "A few seconds ago"
        }
    }
}
package jermaine.technews.ui.articles.util

import android.content.Context
import jermaine.domain.articles.model.Article
import jermaine.domain.articles.util.ArticleUtil
import jermaine.technews.R
import jermaine.technews.ui.articles.adapter.ArticlesListAdapter
import jermaine.technews.ui.articles.model.ArticleViewObject
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.concurrent.TimeUnit


object ViewObjectParser {
    fun articleToViewObjectRepresentation(article: Article, context: Context): ArticleViewObject =
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
                        publishedAtDisplay = getPublishedAtDisplay(publishedAt, context),
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
    private fun getPublishedAtDisplay(publishedAt: String?, context: Context): String {
        publishedAt?.let {
            val publishedAtInstant = Instant.parse(publishedAt)
            val zonedPublishedAt = publishedAtInstant.atZone(ZoneId.systemDefault()) // Converts the UTC datetime string to local time.
            val now = ZonedDateTime.now()

            val elapsedTimeInSeconds = now.toEpochSecond() - zonedPublishedAt.toEpochSecond()

            val days = TimeUnit.SECONDS.toDays(elapsedTimeInSeconds)
            val hours = TimeUnit.SECONDS.toHours(elapsedTimeInSeconds)
            val minutes = TimeUnit.SECONDS.toMinutes(elapsedTimeInSeconds)

            // https://developer.android.com/guide/topics/resources/string-resource#Plurals
            return when {
                days > 0 -> context.resources.getQuantityString(R.plurals.elapsedTimeDays, days.toInt(), days.toInt())
                hours > 0 -> context.resources.getQuantityString(R.plurals.elapsedTimeHours, hours.toInt(), hours.toInt())
                minutes > 0 -> context.resources.getQuantityString(R.plurals.elapsedTimeMinutes, minutes.toInt())
                else -> context.resources.getQuantityString(R.plurals.elapsedTimeSeconds, elapsedTimeInSeconds.toInt())
            }
        }
        return ""
    }
}
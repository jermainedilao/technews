package jermaine.data.articles.db.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import jermaine.domain.articles.model.Article
import jermaine.domain.articles.model.Source

@Entity(tableName = "articles")
data class ArticleModel(
        @PrimaryKey
        var id: String = "",
        var source: Source = Source(),
        var author: String = "",
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var urlToImage: String = "",
        var publishedAt: String = "",
        var bookmarked: Boolean = false
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

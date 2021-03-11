package jermaine.domain.articles.util

import java.security.MessageDigest
import java.util.*


object ArticleUtil {
    fun getIdValue(article: jermaine.domain.articles.model.Article): String {
        with(article) {
            val digest = MessageDigest.getInstance("MD5")
            val message = "$title:$publishedAt".toByteArray()
            return UUID.nameUUIDFromBytes(digest.digest(message)).toString()
        }
    }
}
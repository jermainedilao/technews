package jermaine.data.articles.service

import jermaine.domain.articles.model.Article


data class FetchArticlesApiResponse (val status: String = "",
                                     val totalResults: Int = 0,
                                     val articles: List<Article> = listOf())
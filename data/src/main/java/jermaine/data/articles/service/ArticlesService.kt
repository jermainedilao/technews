package jermaine.data.articles.service

import jermaine.domain.articles.model.Article


interface ArticlesService {
    suspend fun fetchArticles(page: Int): List<Article>
}
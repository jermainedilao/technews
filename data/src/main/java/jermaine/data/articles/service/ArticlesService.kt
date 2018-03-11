package jermaine.data.articles.service

import io.reactivex.Single
import jermaine.domain.articles.model.Article


interface ArticlesService {
    fun fetchArticles(page: Int): Single<List<Article>>
}
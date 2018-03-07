package jermaine.data.articles.service

import io.reactivex.Single
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article


class ArticlesServiceImpl(private val apiService: ApiService) : ArticlesService {
    override fun fetchArticles(): Single<List<Article>> =
            // mock
            // Single.just(gson.fromJson(Article.getTestData(), object : TypeToken<List<Article>>() {}.type))
            apiService.fetchArticles("techcrunch,techradar")
                    .map { it.articles }
}
package jermaine.data.articles.service

import io.reactivex.Single
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article


class ArticlesServiceImpl(private val apiService: ApiService) : ArticlesService {
    override fun fetchArticles(page: Int): Single<List<Article>> =
    // mock
    // Single.just(gson.fromJson(Article.getTestData(), object : TypeToken<List<Article>>() {}.type))
            apiService.fetchArticles("techcrunch,techradar,the-next-web,wired,the-verge", 10, page)
                    .map { it.articles }
}
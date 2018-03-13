package jermaine.data.articles.service

import io.reactivex.Single
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article


class ArticlesServiceImpl(private val apiService: ApiService) : ArticlesService {
    /**
     * Returns list of articles from the api.
     *
     * @param page Page to be returned (used for pagination).
     **/
    override fun fetchArticles(page: Int): Single<List<Article>> =
            apiService.fetchArticles("techcrunch,techradar,the-next-web,wired,the-verge", 10, page)
                    .map { it.articles }
}
package jermaine.data.articles.service

import io.reactivex.Single
import jermaine.data.BuildConfig
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesServiceImpl @Inject constructor(
    private val apiService: ApiService
) : ArticlesService {
    /**
     * Returns list of articles from the api.
     *
     * @param page Page to be returned (used for pagination).
     **/
    override fun fetchArticles(page: Int): Single<List<Article>> =
        apiService.fetchArticles(page, BuildConfig.NEWS_API_KEY)
            .map { it.articles }
}
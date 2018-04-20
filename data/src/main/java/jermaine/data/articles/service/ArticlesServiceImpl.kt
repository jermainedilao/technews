package jermaine.data.articles.service

import android.app.Application
import io.reactivex.Single
import jermaine.data.R
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article


class ArticlesServiceImpl(
        private val apiService: ApiService,
        private val app: Application
) : ArticlesService {
    /**
     * Returns list of articles from the api.
     *
     * @param page Page to be returned (used for pagination).
     **/
    override fun fetchArticles(page: Int): Single<List<Article>> =
            apiService.fetchArticles(page, app.getString(R.string.news_api_key))
                    .map { it.articles }
}
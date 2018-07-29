package jermaine.data.articles.service

import android.content.Context
import io.reactivex.Single
import jermaine.data.R
import jermaine.data.articles.ApiService
import jermaine.domain.articles.model.Article
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesServiceImpl @Inject constructor(
        private val apiService: ApiService,
        private val context: Context
) : ArticlesService {
    /**
     * Returns list of articles from the api.
     *
     * @param page Page to be returned (used for pagination).
     **/
    override fun fetchArticles(page: Int): Single<List<Article>> =
            apiService.fetchArticles(page, context.getString(R.string.news_api_key))
                    .map { it.articles }
}
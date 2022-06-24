package jermaine.data.articles

import io.reactivex.Single
import jermaine.data.articles.service.model.FetchArticlesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top-headlines")
    fun fetchArticles(
        @Query("page") page: Int,
        @Query("apiKey") newsApiKey: String,
        @Query("pageSize") pageSize: Int = 10,
        @Query("sources") sources: String = "techcrunch,techradar,the-next-web,wired,the-verge",
    ): Single<FetchArticlesApiResponse>
}
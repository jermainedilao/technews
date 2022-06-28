package jermaine.data.articles

import jermaine.data.PAGE_SIZE
import jermaine.data.articles.service.model.FetchArticlesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top-headlines")
    suspend fun fetchArticles(
        @Query("page") page: Int,
        @Query("apiKey") newsApiKey: String,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("sources") sources: String = "techcrunch,techradar,the-next-web,wired,the-verge",
    ): FetchArticlesApiResponse
}
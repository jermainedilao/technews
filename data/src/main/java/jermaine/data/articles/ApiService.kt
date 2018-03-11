package jermaine.data.articles

import io.reactivex.Single
import jermaine.data.articles.service.FetchArticlesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/v2/top-headlines")
    fun fetchArticles(
            @Query("sources") sources: String,
//            @Query("category") category: String,
//            @Query("country") country: String,
            @Query("pageSize") pageSize: Int,
            @Query("page") page: Int
    ): Single<FetchArticlesApiResponse>
}
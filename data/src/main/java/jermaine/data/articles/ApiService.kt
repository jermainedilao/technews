package jermaine.data.articles

import io.reactivex.Single
import jermaine.data.articles.service.model.FetchArticlesApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @POST("/api/v1/newslist")
    @FormUrlEncoded
    fun fetchArticles(
//            @Query("sources") sources: String,
//            @Query("category") category: String,
//            @Query("country") country: String,
//            @Query("pageSize") pageSize: Int,
            @Field("page") page: Int,
            @Field("news_api_key") newsApiKey: String
    ): Single<FetchArticlesApiResponse>
}
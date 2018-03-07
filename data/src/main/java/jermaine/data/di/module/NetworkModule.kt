package jermaine.data.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import jermaine.data.R
import jermaine.data.articles.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule(val context: Context) {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()

                val requestBuilder = original.newBuilder()
                        .addHeader("X-Api-Key", context.getString(R.string.news_api_key))

                it.proceed(requestBuilder.build())
            }
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
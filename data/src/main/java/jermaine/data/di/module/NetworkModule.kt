package jermaine.data.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import jermaine.data.R
import jermaine.data.articles.ApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule(val app: Application) {
    @Singleton
    @Provides
    fun provideOkHttpCache(): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MiB
        return Cache(app.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()

                val requestBuilder = original.newBuilder()
                        .addHeader("X-Api-Key", app.getString(R.string.news_api_key))

                it.proceed(requestBuilder.build())
            }
            .cache(cache)
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
package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.data.articles.ApiService
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl
import javax.inject.Singleton


@Module
class ServiceModule {
    @Provides
    @Singleton
    fun providesArticlesService(apiService: ApiService): ArticlesService = ArticlesServiceImpl(apiService)
}
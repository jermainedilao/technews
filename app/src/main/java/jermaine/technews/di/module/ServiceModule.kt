package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl


@Module
class ServiceModule {
    @Provides
    fun providesArticlesService(articlesService: ArticlesServiceImpl): ArticlesService = articlesService
}
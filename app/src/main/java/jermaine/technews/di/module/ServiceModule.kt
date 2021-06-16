package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Provides
    fun providesArticlesService(articlesService: ArticlesServiceImpl): ArticlesService = articlesService
}
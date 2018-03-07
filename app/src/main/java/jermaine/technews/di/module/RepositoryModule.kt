package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.data.articles.ArticlesRepositoryImpl
import jermaine.data.articles.service.ArticlesService
import jermaine.domain.articles.ArticlesRepository
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesArticlesRepository(articlesService: ArticlesService): ArticlesRepository =
            ArticlesRepositoryImpl(articlesService)
}
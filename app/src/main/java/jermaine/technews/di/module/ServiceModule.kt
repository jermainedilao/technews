package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl

@InstallIn(ApplicationComponent::class)
@Module
class ServiceModule {
    @Provides
    fun providesArticlesService(articlesService: ArticlesServiceImpl): ArticlesService = articlesService
}
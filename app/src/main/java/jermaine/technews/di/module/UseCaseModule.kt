package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.domain.articles.ArticlesRepository
import jermaine.domain.articles.interactors.FetchArticlesListUseCase
import javax.inject.Singleton


@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun providesFetchArticlesListUseCase(articlesRepository: ArticlesRepository): FetchArticlesListUseCase =
            FetchArticlesListUseCase(articlesRepository)
}
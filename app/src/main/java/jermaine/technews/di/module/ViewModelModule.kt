package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.domain.articles.interactors.FetchArticlesListUseCase
import jermaine.technews.ui.articles.ArticlesViewModel
import javax.inject.Singleton


@Module
class ViewModelModule{
    @Singleton
    @Provides
    fun providesArticlesViewModel(fetchArticlesListUseCase: FetchArticlesListUseCase): ArticlesViewModel =
            ArticlesViewModel(fetchArticlesListUseCase)
}
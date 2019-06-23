package jermaine.technews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import jermaine.technews.di.ViewModelFactory
import jermaine.technews.di.ViewModelKey
import jermaine.technews.ui.articles.ArticlesViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    protected abstract fun articlesViewModel(articlesViewModel: ArticlesViewModel): ViewModel
}
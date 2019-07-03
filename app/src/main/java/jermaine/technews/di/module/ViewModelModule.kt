package jermaine.technews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import jermaine.technews.di.ViewModelFactory
import jermaine.technews.di.ViewModelKey
import jermaine.technews.ui.articles.ArticlesViewModel
import jermaine.technews.ui.bookmarks.BookmarksViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    protected abstract fun articlesViewModel(articlesViewModel: ArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    protected abstract fun bookmarksViewModel(bookmarksViewModel: BookmarksViewModel): ViewModel
}
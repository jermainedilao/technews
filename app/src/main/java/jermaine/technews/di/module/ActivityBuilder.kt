package jermaine.technews.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jermaine.technews.ui.articles.ArticlesActivityModule
import jermaine.technews.ui.articles.ArticlesListActivity
import jermaine.technews.ui.bookmarks.BookmarksActivityModule
import jermaine.technews.ui.bookmarks.BookmarksListActivity


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [ArticlesActivityModule::class])
    abstract fun contributesArticlesListActivity(): ArticlesListActivity

    @ContributesAndroidInjector(modules = [BookmarksActivityModule::class])
    abstract fun contributesBookmarksListActivity(): BookmarksListActivity
}
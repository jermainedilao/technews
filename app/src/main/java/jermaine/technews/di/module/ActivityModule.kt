package jermaine.technews.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jermaine.technews.ui.articles.ArticlesListActivity
import jermaine.technews.ui.bookmarks.BookmarksListActivity


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributesArticlesListActivity(): ArticlesListActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarksListActivity(): BookmarksListActivity
}
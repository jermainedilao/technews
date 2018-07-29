package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.data.articles.db.ArticlesDao
import jermaine.data.articles.db.ArticlesDaoImpl


@Module
class DaoModule {
    @Provides
    fun providesArticlesDao(articlesDao: ArticlesDaoImpl): ArticlesDao = articlesDao
}
package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jermaine.data.articles.db.ArticlesDao
import jermaine.data.articles.db.ArticlesDaoImpl

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {
    @Provides
    fun providesArticlesDao(articlesDao: ArticlesDaoImpl): ArticlesDao = articlesDao
}
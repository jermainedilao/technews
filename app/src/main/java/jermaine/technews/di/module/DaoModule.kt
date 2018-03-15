package jermaine.technews.di.module

import dagger.Module
import dagger.Provides
import jermaine.data.articles.db.ArticlesDao
import jermaine.data.articles.db.ArticlesDaoImpl
import jermaine.data.articles.db.room.ArticleRoomDao
import javax.inject.Singleton


@Module
class DaoModule {
    @Provides
    @Singleton
    fun providesArticlesDao(articleRoomDao: ArticleRoomDao): ArticlesDao =
            ArticlesDaoImpl(articleRoomDao)
}
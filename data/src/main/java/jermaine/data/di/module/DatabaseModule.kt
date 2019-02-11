package jermaine.data.di.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import jermaine.data.articles.db.room.ArticleRoomDao
import jermaine.data.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(app: Application): AppDatabase = Room.databaseBuilder(
            app, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesArticleRoomDao(appDatabase: AppDatabase): ArticleRoomDao = appDatabase.articleDao()
}
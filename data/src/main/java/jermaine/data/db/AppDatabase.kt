package jermaine.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import jermaine.data.articles.db.model.ArticleModel
import jermaine.data.articles.db.room.ArticleRoomDao

@Database(entities = [ArticleModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val DATABASE_NAME = "app_database"
    }

    abstract fun articleDao(): ArticleRoomDao
}
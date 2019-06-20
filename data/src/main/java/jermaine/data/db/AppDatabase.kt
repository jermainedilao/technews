package jermaine.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
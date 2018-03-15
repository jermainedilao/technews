package jermaine.data.articles.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import jermaine.data.articles.db.model.ArticleModel


@Dao
interface ArticleRoomDao {
    @Query("SELECT * FROM articles")
    fun fetchArticles(): Single<List<ArticleModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveArticle(article: ArticleModel)
}
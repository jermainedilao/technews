package jermaine.data.articles.db.room

import androidx.room.*
import io.reactivex.Single
import jermaine.data.articles.db.model.ArticleModel


@Dao
interface ArticleRoomDao {
    @Query("SELECT * FROM articles")
    fun fetchArticles(): Single<List<ArticleModel>>

    @Query("SELECT * FROM articles")
    suspend fun fetchArticlesCo(): List<ArticleModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveArticle(article: ArticleModel)

    @Delete
    fun deleteArticle(article: ArticleModel)
}
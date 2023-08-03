package com.ozanarik.MvvmNewsApp.data.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozanarik.MvvmNewsApp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article):Long

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM `articles_db.db`")
    fun getAllSavedNews():Flow<List<Article>>


}
package com.ozanarik.MvvmNewsApp.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ozanarik.MvvmNewsApp.model.Article
import com.ozanarik.MvvmNewsApp.utils.Converters

@Database(entities =
    [Article::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase:RoomDatabase() {

    abstract fun getArticleDao():ArticleDao

}
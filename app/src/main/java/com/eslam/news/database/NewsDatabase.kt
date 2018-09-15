package com.eslam.news.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.eslam.news.model.Article

@Database(
        entities = [Article::class],
        version = 1,
        exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticleDao

    companion object {
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createDatabase(context).also { INSTANCE = it }
                }

        private fun createDatabase(context: Context): NewsDatabase =
                Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "News.db").build()
    }
}
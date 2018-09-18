package com.eslam.news.database

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.eslam.news.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articles: List<Article?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): DataSource.Factory<Int, Article>

    @Query("SELECT * FROM articles WHERE (favorite = 1) ORDER BY publishedAt DESC")
    fun getFavoriteArticles(): LiveData<List<Article>>
}
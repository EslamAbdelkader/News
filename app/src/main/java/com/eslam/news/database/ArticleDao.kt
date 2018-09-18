package com.eslam.news.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.eslam.news.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: Article)

    @Delete()
    fun delete(article: Article)

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getArticles(): LiveData<List<Article>>
}
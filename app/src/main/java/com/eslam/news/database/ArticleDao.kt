package com.eslam.news.database

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.eslam.news.model.Article
import io.reactivex.Maybe

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articles: List<Article?>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): DataSource.Factory<Int, Article>

    @Query("SELECT * FROM articles WHERE (favorite = 1) ORDER BY publishedAt")
    fun getFavoriteArticles(): Maybe<List<Article>>
}
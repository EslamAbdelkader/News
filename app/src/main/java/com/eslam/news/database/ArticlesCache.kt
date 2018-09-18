package com.eslam.news.database

import android.annotation.SuppressLint
import android.arch.paging.DataSource
import android.content.SharedPreferences
import com.eslam.news.model.Article
import java.util.concurrent.Executor

open class ArticlesCache(private val database: NewsDatabase, private val executor: Executor, private val sharedPreferences: SharedPreferences) {
    fun insertArticles(articles: List<Article?>?) {
        executor.execute {
            database.articlesDao().insert(articles)
        }
    }

    fun updateArticle(article: Article) {
        executor.execute {
            database.articlesDao().update(article)
        }
    }

    fun getFavoriteArticles() = database.articlesDao().getFavoriteArticles()

    fun getAllArticles(): DataSource.Factory<Int, Article> = database.articlesDao().getAllArticles()

    fun getNextPage(): Int {
        return sharedPreferences.getInt(NEXT_PAGE_KEY, 1)
    }

    @SuppressLint("ApplySharedPref")
    fun saveNextPage(nextPage: Int) {
        sharedPreferences.edit().putInt("NEXT_PAGE", nextPage).commit()
    }

    companion object {
        private const val NEXT_PAGE_KEY = "NEXT_PAGE"
    }

}
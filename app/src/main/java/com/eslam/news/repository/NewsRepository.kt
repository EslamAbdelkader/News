package com.eslam.news.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.SharedPreferences
import com.eslam.news.api.ArticlesBoundaryCallback
import com.eslam.news.api.NewsApi
import com.eslam.news.database.ArticlesCache
import com.eslam.news.model.Article

class NewsRepository(private val articlesCache: ArticlesCache,
                     private val articlesApi: NewsApi) {
    fun getArticles(): LiveData<PagedList<Article>> {
        val dataSourceFactory = articlesCache.getAllArticles()
        val articlesBoundaryCallback = ArticlesBoundaryCallback(
                articlesCache, articlesApi, articlesCache.getNextPage())
        return LivePagedListBuilder(dataSourceFactory, DEFAULT_PAGE_SIZE)
                .setBoundaryCallback(articlesBoundaryCallback)
                .build()
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 30
    }

}
package com.eslam.news.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import com.eslam.news.api.ArticlesDataSourceFactory
import com.eslam.news.api.NewsApi
import com.eslam.news.database.ArticlesCache
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.model.Result

class NewsRepository(private val articlesCache: ArticlesCache,
                     private val articlesApi: NewsApi) {
    fun getArticles(): Result<Article> {
        val networkState = MutableLiveData<NetworkState>()
        val dataSourceFactory = ArticlesDataSourceFactory(articlesApi, networkState) { it.favorite = true }
        val data = LivePagedListBuilder(dataSourceFactory, DEFAULT_LOCAL_PAGE_SIZE)
//                .setBoundaryCallback(articlesBoundaryCallback)
                .build()
        return Result(data, networkState)
    }

    companion object {
        private const val DEFAULT_LOCAL_PAGE_SIZE = 10
    }

}
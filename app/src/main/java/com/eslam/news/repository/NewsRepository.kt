package com.eslam.news.repository

import android.arch.lifecycle.MutableLiveData
import com.eslam.news.api.NewsApi
import com.eslam.news.api.NewsApi.Companion.API_KEY
import com.eslam.news.api.NewsApi.Companion.DEFAULT_LANGUAGE
import com.eslam.news.api.NewsApi.Companion.DEFAULT_NETWORK_PAGES_IZE
import com.eslam.news.api.NewsApi.Companion.DEFAULT_SORT
import com.eslam.news.api.NewsApi.Companion.DEFAULT_SOURCES
import com.eslam.news.database.ArticlesCache
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.model.Result
import com.eslam.news.utils.ioSubscribe

open class NewsRepository(private val articlesCache: ArticlesCache,
                     private val articlesApi: NewsApi) {

    private var page = 1

    private var requestInProgress = false

    open val result = Result<Article>(MutableLiveData(), MutableLiveData())

    /**
     * Loads more articles from network, makes sure one request is in progress on time.
     * Updates both LiveData of articles and this of NetworkState with the result.
     * Also keeps track and increases the page number on success.
     */
    open fun loadMoreArticles() {
        result.networkState.value = NetworkState.loading()
        if (!requestInProgress) {
            requestInProgress = true
            getDefaultNews(page).ioSubscribe().subscribe({
                page++
                requestInProgress = false
                result.articles.postValue(it.articles)
                result.networkState.postValue(NetworkState.success())
            }, {
                requestInProgress = false
                result.articles.postValue(emptyList())
                result.networkState.postValue(NetworkState.error(it.message
                        ?: "Unknown Error"))
            })
        }
    }

    /**
     * Gets favorite articles stored in database
     */
    open fun getFavoriteArticles() = articlesCache.getFavoriteArticles()

    /**
     * Stores or deletes articles based on its favorite item
     */
    open fun updateArticle(article: Article){
        articlesCache.updateArticle(article)
    }

    /**
     * Helper function for getting news with default parameters
     */
    private fun getDefaultNews(page: Int) =
            articlesApi.getNews(API_KEY, DEFAULT_SOURCES, DEFAULT_LANGUAGE, DEFAULT_SORT, DEFAULT_NETWORK_PAGES_IZE, page)

}
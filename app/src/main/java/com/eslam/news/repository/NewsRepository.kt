package com.eslam.news.repository

import android.arch.lifecycle.MutableLiveData
import com.eslam.news.api.NewsApi
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

    open fun getFavoriteArticles() = articlesCache.getFavoriteArticles()

    open fun updateArticle(article: Article){
        articlesCache.updateArticle(article)
    }

    companion object {
        private const val DEFAULT_LOCAL_PAGE_SIZE = 10
        private const val DEFAULT_NETWORK_PAGES_IZE = 30
        private const val API_KEY = "641b974a714a4946910423a67f938fe1"
        private const val DEFAULT_SOURCES = "usa-today"
        private const val DEFAULT_LANGUAGE = "en"
        private const val DEFAULT_SORT = "publishedAt"
    }


    private fun getDefaultNews(page: Int) =
            articlesApi.getNews(API_KEY, DEFAULT_SOURCES, DEFAULT_LANGUAGE, DEFAULT_SORT, DEFAULT_NETWORK_PAGES_IZE, page)

}
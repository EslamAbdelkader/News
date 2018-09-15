package com.eslam.news.api

import android.arch.paging.PagedList
import com.eslam.news.database.ArticlesCache
import com.eslam.news.model.Article
import com.eslam.news.utils.ioSubscribe

class ArticlesBoundaryCallback(private val cache: ArticlesCache,
                               private val api: NewsApi,
                               private var nextPage: Int = 1) : PagedList.BoundaryCallback<Article>() {
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        getDataAndSaveToCache()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        getDataAndSaveToCache()
    }

    private fun getDataAndSaveToCache() {
        if (!isRequestInProgress) {
            isRequestInProgress = true
            getDefaultNews(nextPage).ioSubscribe().subscribe {
                nextPage++
                cache.insertArticles(it.articles)
                cache.saveNextPage(nextPage)
                isRequestInProgress = false
            }
        }
    }

    private fun getDefaultNews(page: Int) =
            api.getNews(API_KEY, DEFAULT_SOURCES, DEFAULT_LANGUAGE, DEFAULT_SORT, DEFAULT_NETWORK_PAGES_IZE, page)

    companion object {
        private const val DEFAULT_NETWORK_PAGES_IZE = 30
        private const val API_KEY = "641b974a714a4946910423a67f938fe1"
        private const val DEFAULT_SOURCES = "usa-today"
        private const val DEFAULT_LANGUAGE = "en"
        private const val DEFAULT_SORT = "publishedAt"
    }
}
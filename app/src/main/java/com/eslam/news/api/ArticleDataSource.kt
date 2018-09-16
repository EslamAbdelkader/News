package com.eslam.news.api

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.utils.ioSubscribe

class ArticleDataSource(private val api: NewsApi,
                        private val networkState: MutableLiveData<NetworkState>,
                        private val preSubmitProcess: (Article) -> Unit
) : PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        networkState.postValue(NetworkState.loading())
        getDefaultNews(1).ioSubscribe().subscribe({
            networkState.postValue(NetworkState.success())
            it.articles?.forEach { preSubmitProcess }
            callback.onResult(it.articles!!, 1, 2)
        }, { networkState.postValue(NetworkState.error(it?.message ?: "Unknown Error")) })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        networkState.postValue(NetworkState.loading())
        getDefaultNews(params.key).ioSubscribe().subscribe({
            networkState.postValue(NetworkState.success())
            it.articles?.forEach { preSubmitProcess }
            callback.onResult(it.articles!!, params.key + 1)
        }, { networkState.postValue(NetworkState.error(it?.message ?: "Unknown Error")) })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        // empty implementation
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
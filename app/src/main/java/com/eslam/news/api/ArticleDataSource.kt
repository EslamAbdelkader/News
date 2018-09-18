package com.eslam.news.api

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState

class ArticleDataSource(private val api: NewsApi,
                        private val networkState: MutableLiveData<NetworkState>,
                        private val preSubmitProcess: (Article) -> Unit
) : PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
//        networkState.postValue(NetworkState.loading())
//        getDefaultNews(1).ioSubscribe().subscribe({
//            networkState.postValue(NetworkState.success())
//            it.articles?.forEach { preSubmitProcess }
//            callback.onResult(it.articles!!, 1, 2)
//        }, { networkState.postValue(NetworkState.error(it?.message ?: "Unknown Error")) })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
//        networkState.postValue(NetworkState.loading())
//        getDefaultNews(params.key).ioSubscribe().subscribe({
//            networkState.postValue(NetworkState.success())
//            it.articles?.forEach { preSubmitProcess }
//            callback.onResult(it.articles!!, params.key + 1)
//        }, { networkState.postValue(NetworkState.error(it?.message ?: "Unknown Error")) })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        // empty implementation
    }


}
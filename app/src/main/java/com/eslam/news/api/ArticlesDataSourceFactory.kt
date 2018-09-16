package com.eslam.news.api

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState


class ArticlesDataSourceFactory(private val api: NewsApi, private val networkState: MutableLiveData<NetworkState>, private val preSubmitProcess: (Article)->Unit) : DataSource.Factory<Int,Article>(){
    override fun create(): DataSource<Int, Article> {
        return ArticleDataSource(api, networkState, preSubmitProcess)
    }
}
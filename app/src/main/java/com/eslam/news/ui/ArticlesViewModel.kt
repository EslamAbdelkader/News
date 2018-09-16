package com.eslam.news.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.eslam.news.di.Injection
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState

class ArticlesViewModel : ViewModel() {
    private val repository = Injection.provideRepository()
    private val result = repository.getArticles()
    val articles: LiveData<PagedList<Article>> = result.articles
    val networkState: LiveData<NetworkState> = result.networkState

}
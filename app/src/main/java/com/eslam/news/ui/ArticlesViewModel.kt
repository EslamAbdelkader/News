package com.eslam.news.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.eslam.news.di.Injection
import com.eslam.news.model.Article

class ArticlesViewModel : ViewModel() {
    private val repository = Injection.provideRepository()
    val articles : LiveData<PagedList<Article>> = repository.getArticles()

}
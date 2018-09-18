package com.eslam.news.di

import android.app.Application
import com.eslam.news.api.Client
import com.eslam.news.api.NewsApi
import com.eslam.news.database.ArticlesCache
import com.eslam.news.database.NewsDatabase
import com.eslam.news.repository.NewsRepository
import com.eslam.news.ui.viewmodel.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    lateinit var application: Application

    private var newsRepository: NewsRepository? = null

    object LOCK

    private fun provideNewsApi() = Client.getInstance().create(NewsApi::class.java)

    private fun provideArticlesCache() = ArticlesCache(getNewsDatabase(), Executors.newSingleThreadExecutor())

    private fun getNewsDatabase() = NewsDatabase.getInstance(application)

    private fun provideRepository(): NewsRepository {
        synchronized(LOCK) {
            return newsRepository
                    ?: NewsRepository(provideArticlesCache(), provideNewsApi()).also { newsRepository = it }
        }
    }

    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(provideRepository())
    }
}
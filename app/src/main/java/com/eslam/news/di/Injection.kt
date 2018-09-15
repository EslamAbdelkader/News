package com.eslam.news.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.eslam.news.api.Client
import com.eslam.news.api.NewsApi
import com.eslam.news.database.ArticlesCache
import com.eslam.news.database.NewsDatabase
import com.eslam.news.repository.NewsRepository
import java.util.concurrent.Executors

object Injection {

    lateinit var application: Application

    private fun provideSharedPreferences(): SharedPreferences =
            application.getSharedPreferences("DEFAULT_SHARD_PREFERENCES", Context.MODE_PRIVATE)

    private fun provideNewsApi() = Client.getInstance().create(NewsApi::class.java)

    private fun provideArticlesCache() = ArticlesCache(getNewsDatabase(), Executors.newSingleThreadExecutor(), provideSharedPreferences())

    private fun getNewsDatabase() = NewsDatabase.getInstance(application)

    fun provideRepository() = NewsRepository( provideArticlesCache(), provideNewsApi())
}
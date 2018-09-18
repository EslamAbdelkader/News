package com.eslam.news.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import com.eslam.news.api.NewsApi
import com.eslam.news.api.NewsApi.Companion.API_KEY
import com.eslam.news.api.NewsApi.Companion.DEFAULT_LANGUAGE
import com.eslam.news.api.NewsApi.Companion.DEFAULT_NETWORK_PAGES_IZE
import com.eslam.news.api.NewsApi.Companion.DEFAULT_SORT
import com.eslam.news.api.NewsApi.Companion.DEFAULT_SOURCES
import com.eslam.news.database.ArticlesCache
import com.eslam.news.model.Article
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doNothing

class NewsRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val api = mock<NewsApi>()
    val cache = mock<ArticlesCache>()
    val repository = NewsRepository(cache, api)

    @Test
    fun testRequestDoesNotFireManyTimesWhileInProgress() {
        whenever(api.getNews(API_KEY, DEFAULT_SOURCES, DEFAULT_LANGUAGE,
                DEFAULT_SORT, DEFAULT_NETWORK_PAGES_IZE, 1)).thenReturn(mock())
        repository.loadMoreArticles()
        repository.loadMoreArticles()
        repository.loadMoreArticles()
        verify(api, times(1)).getNews(API_KEY, DEFAULT_SOURCES, DEFAULT_LANGUAGE,
                DEFAULT_SORT, DEFAULT_NETWORK_PAGES_IZE, 1)
    }


    @Test
    fun getFavoriteArticles() {
        val mock = mock<LiveData<List<Article>>>()
        whenever(cache.getFavoriteArticles()).thenReturn(mock)
        assertEquals(repository.getFavoriteArticles(),mock)
    }

    @Test
    fun testUpdateArticleNotifiesDatabase() {
        val article = Article("article", "", "", "", true)
        doNothing().whenever(cache).updateArticle(article)
        repository.updateArticle(article)
        verify(cache, times(1)).updateArticle(article)
    }
}
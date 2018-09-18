package com.eslam.news.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.model.Result
import com.eslam.news.repository.NewsRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class ArticlesViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var repository: NewsRepository
    private lateinit var networkStateLiveData: MutableLiveData<NetworkState>
    private lateinit var networkArticlesLiveData: MutableLiveData<List<Article?>>


    @Before
    fun init() {
        repository = mock(NewsRepository::class.java)

        val articlesLiveData = MutableLiveData<List<Article>>()
        `when`(repository.getFavoriteArticles()).thenReturn(articlesLiveData)
        doNothing().whenever(repository).loadMoreArticles()
        networkArticlesLiveData = MutableLiveData()
        networkStateLiveData = MutableLiveData()
        whenever(repository.result).thenReturn(Result(networkArticlesLiveData, networkStateLiveData))

        viewModel = ArticlesViewModel(repository)
    }

    @Test
    fun testArticlesLoadedOnLastVisibleItemCloseToEndOfList() {
        viewModel.listScrolled(3, 14, 20)
        verify(repository, times(1)).loadMoreArticles()
    }

    @Test
    fun testArticlesNotLoadedOnLastVisibleItemNotCloseToEndOfList() {
        viewModel.listScrolled(3, 7, 20)
        verify(repository, times(0)).loadMoreArticles()
    }

    @Test
    fun testToggleItem() {
        val article = Article("article", "", "", "", false)
        doNothing().whenever(repository).updateArticle(article)
        viewModel.toggleFavorite(article)
        assertEquals(article.favorite, true)
        viewModel.toggleFavorite(article)
        assertEquals(article.favorite, false)
        verify(repository, times(2)).updateArticle(article)
    }

    @Test
    fun testViewModelAddsNetworkDataToSourceOfTruthData() {
        val firstList = mutableListOf<Article>()
        val secondList = mutableListOf<Article>()
        val article1 = Article("article1", "", "", "", false)
        val article2 = article1.copy("article2")
        val article3 = article1.copy("article3")
        val article4 = article1.copy("article4")
        firstList.add(article1)
        firstList.add(article2)
        secondList.add(article3)
        secondList.add(article4)

        networkArticlesLiveData.value = firstList
        networkArticlesLiveData.value = secondList

        assertEquals(viewModel.articles.value?.size,4)
        assertEquals(viewModel.articles.value?.get(0),article1)
        assertEquals(viewModel.articles.value?.get(1),article2)
        assertEquals(viewModel.articles.value?.get(2),article3)
        assertEquals(viewModel.articles.value?.get(3),article4)
    }

    @Test
    fun testChangeInDatabaseReflectInSourceOfTruth(){
        val article = Article("article1", "", "", "", false)
        networkArticlesLiveData.value = mutableListOf(article)
        viewModel.toggleFavorite(article)

        assertEquals(viewModel.articles.value?.size,1)
        assertEquals(viewModel.articles.value?.get(0),article.copy(favorite = true))
    }
}
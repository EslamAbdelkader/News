package com.eslam.news.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.eslam.news.model.Article
import com.eslam.news.model.NetworkState
import com.eslam.news.repository.NewsRepository

class ArticlesViewModel(private val repository: NewsRepository) : ViewModel() {
    // Network result
    private var result = repository.result
    // New articles retrieved from network
    private val networkArticles: LiveData<List<Article?>?> by lazy { result.articles }
    // Network state (Loading, Error, Success)
    val networkState: LiveData<NetworkState> by lazy { result.networkState }
    // Favorite articles saved in Database
    val favorites: LiveData<List<Article>> = repository.getFavoriteArticles()
    // Source Of Truth
    val articles: MutableLiveData<List<Article?>?> = MutableLiveData()

    init {
        networkArticles.observeForever {
            // Adds articles from network to "Source of Truth list" after modifying the favorite
            // attribute based on the data stored in database
            val list = mutableListOf<Article?>()
            list.addAll(articles.value ?: emptyList())
            list.addAll(it ?: emptyList())
            list.forEach {
                it?.favorite = favorites.value?.contains(it) == true
            }
            articles.postValue(list)
        }

        favorites.observeForever {
            // Notifies "Source of Truth list" with any change in data stored in database
            favoriteArticles ->
            articles.value?.forEach { it?.favorite = favoriteArticles?.contains(it) }
        }
    }

    /**
     * Asks the repository to load more articles from network
     */
    private fun loadMoreArticles() = repository.loadMoreArticles()

    /**
     * Checks if it's time to load more data (When recyclerView is scrolled enough)
     */
    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            loadMoreArticles()
        }
    }

    /**
     * Favorite/Un-favorite article and saves it in database
     */
    fun toggleFavorite(item: Article) {
        item.favorite = (item.favorite != true)
        repository.updateArticle(item)
    }


    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

}
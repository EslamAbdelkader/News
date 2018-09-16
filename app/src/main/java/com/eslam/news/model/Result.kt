package com.eslam.news.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Result<T>(val articles: LiveData<PagedList<T>>, val networkState: LiveData<NetworkState>)
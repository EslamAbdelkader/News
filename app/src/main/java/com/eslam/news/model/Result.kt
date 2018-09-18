package com.eslam.news.model

import android.arch.lifecycle.MutableLiveData

data class Result<T>(val articles: MutableLiveData<List<T?>>, val networkState: MutableLiveData<NetworkState>)
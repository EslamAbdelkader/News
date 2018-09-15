package com.eslam.news.api

import com.eslam.news.model.Article
import com.google.gson.annotations.SerializedName


data class NewsResponse(
        @SerializedName("status") var status: String?,
        @SerializedName("totalResults") var totalResults: Int?,
        @SerializedName("articles") var articles: List<Article?>?
)
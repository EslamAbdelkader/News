package com.eslam.news.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class Article(
        @PrimaryKey @SerializedName("title") var title: String,
        @SerializedName("url") var url: String?,
        @SerializedName("urlToImage") var urlToImage: String?,
        @SerializedName("publishedAt") var publishedAt: String?,
        var favorite: Boolean?
)

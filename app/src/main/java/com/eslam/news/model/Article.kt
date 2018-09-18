package com.eslam.news.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "articles")
data class Article(
        @PrimaryKey @SerializedName("title") var title: String,
        @SerializedName("url") var url: String?,
        @SerializedName("urlToImage") var urlToImage: String?,
        @SerializedName("publishedAt") var publishedAt: String?,
        var favorite: Boolean?
){
    override fun equals(other: Any?): Boolean {
        return other is Article && other.title == title
    }

    /**
     * Parses and returns publish date as a Java Date object
     */
    @SuppressLint("SimpleDateFormat")
    fun getDate(): Date {
        return SimpleDateFormat(DATE_FORMAT).parse(publishedAt)
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}

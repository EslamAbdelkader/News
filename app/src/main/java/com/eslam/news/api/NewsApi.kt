package com.eslam.news.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("sources") source: String,
                @Query("language") lang: String,
                @Query("sortBy") sortBy: String,
                @Query("pageSize") pageSize: Int,
                @Query("page") page: Int): Observable<NewsResponse>

}
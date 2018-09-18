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


    companion object {
        const val DEFAULT_NETWORK_PAGES_IZE = 30
        const val API_KEY = "641b974a714a4946910423a67f938fe1"
        const val DEFAULT_SOURCES = "usa-today"
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_SORT = "publishedAt"
    }
}
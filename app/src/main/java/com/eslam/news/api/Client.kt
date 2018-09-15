package com.eslam.news.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createClient().also { INSTANCE = it }
                }

        private fun createClient(): Retrofit {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }
}
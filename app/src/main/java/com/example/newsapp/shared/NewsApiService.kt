package com.example.newsapp.shared

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 30,
        @Query("apiKey") apiKey: String
    ): Call<News>
}


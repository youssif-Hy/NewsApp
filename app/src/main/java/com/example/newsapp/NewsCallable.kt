package com.example.newsapp


import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    @GET("/v2/top-headlines?country=eg&category=general&apiKey=3d13a9e807074efe8afcba2dfda544b3&pageSize=30")
    fun getNews () : Call<News>

}
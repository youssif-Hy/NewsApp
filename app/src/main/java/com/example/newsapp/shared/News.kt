package com.example.newsapp.shared

data class News(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?

)

data class Article(
    val title: String?,
    val url: String?,
    val urlToImage: String?,
)


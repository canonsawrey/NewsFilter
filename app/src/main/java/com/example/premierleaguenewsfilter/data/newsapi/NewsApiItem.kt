package com.example.premierleaguenewsfilter.data.newsapi

data class NewsApiItem(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String
)

data class Source(
    val id: Int?,
    val name: String
)


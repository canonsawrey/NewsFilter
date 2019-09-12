package com.example.premierleaguenewsfilter.data.newsapi

import com.example.premierleaguenewsfilter.dashboard.home.NewsItem

data class NewsApiItem(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {

    fun toNewsItem(): List<NewsItem> {
        val stories = mutableListOf<NewsItem>()
        for (i in articles) {
            if (!i.title.isNullOrEmpty() && !i.description.isNullOrEmpty() && !i.url.isNullOrEmpty()) {
                stories.add(NewsItem(i.author, i.title, i.description, i.url))
            }
        }
        return stories
    }
}

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?
)

data class Source(
    val id: String?,
    val name: String?
)


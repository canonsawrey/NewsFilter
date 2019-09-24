package com.csawrey.newsstreams.data.newsapi

import com.csawrey.newsstreams.data.room.DatabaseCachedStoryItem

data class NewsApiItem(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {

    fun toDatabaseCachedStoryItem(parentId: Long): List<DatabaseCachedStoryItem> {
        val stories = mutableListOf<DatabaseCachedStoryItem>()
        for (i in articles) {
            if (!i.title.isNullOrEmpty() && !i.description.isNullOrEmpty() && !i.url.isNullOrEmpty() && !i.urlToImage.isNullOrEmpty()) {
                stories.add(
                    DatabaseCachedStoryItem(
                        0,
                        parentId,
                        i.author ?: "",
                        i.title,
                        i.description,
                        i.url,
                        i.urlToImage
                    )
                )
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


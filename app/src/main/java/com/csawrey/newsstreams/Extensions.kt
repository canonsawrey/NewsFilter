package com.csawrey.newsstreams


import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.dashboard.streams.single_stream.NewsItem
import com.csawrey.newsstreams.data.room.CachedResultItemsDao
import com.csawrey.newsstreams.data.room.DatabaseCachedStoryItem
import com.csawrey.newsstreams.data.room.DatabaseSearchItem

fun DatabaseSearchItem.toSearchItem() =
    SearchItem(this.uid, this.keyword, this.sort.toSort(), this.weight.toWeight(), this.daysOld)

fun DatabaseCachedStoryItem.toNewsItem() =
    NewsItem(this.author, this.title, this.description, this.url, this.imageUrl)

fun String.toSort(): Sort {
    return when (this) {
        "relevancy" -> Sort.RELEVANT
        "popularity" -> Sort.POPULAR
        "publishedAt" -> Sort.RECENT
        else -> throw IllegalArgumentException("Unrecognized weight string: $this")
    }
}

fun String.toWeight(): Weight {
    return when (this) {
        "small" -> Weight.SMALL
        "average" -> Weight.AVERAGE
        "large" -> Weight.LARGE
        else -> throw IllegalArgumentException("Unrecognized weight string: $this")
    }
}

fun toDoList() {
    TODO("Add 'powered by newsapi' or w.e.t.f. they wanted")
    TODO("Hide API key")
}

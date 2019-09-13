package com.csawrey.newsfilter


import com.csawrey.newsfilter.dashboard.edit_feeds.SearchItem
import com.csawrey.newsfilter.data.room.DatabaseSearchItem

fun DatabaseSearchItem.toSearchItem(): SearchItem {
    return SearchItem(
        this.uid,
        this.keyword)
}

fun toDoList() {
    TODO("Add 'powered by newsapi' or w.e.t.f. they wanted")
    TODO("Hide API key")
}

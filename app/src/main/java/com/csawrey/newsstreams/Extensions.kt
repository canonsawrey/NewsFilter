package com.csawrey.newsstreams


import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.data.room.DatabaseSearchItem

fun DatabaseSearchItem.toSearchItem(): SearchItem {
    return SearchItem(
        this.uid,
        this.keyword)
}

fun toDoList() {
    TODO("Add 'powered by newsapi' or w.e.t.f. they wanted")
    TODO("Hide API key")
}

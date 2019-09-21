package com.csawrey.newsstreams.mock

import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.data.room.DatabaseNewsStream

val mockedStreams = listOf(
    DatabaseNewsStream(1, "Northeastern")
)

val mockedSearchItems = listOf(
    SearchItem(0,"Northeastern University"),
    SearchItem(0,"Massachusetts"),
    SearchItem(0,"Healthcare"),
    SearchItem(0, "2020 Election")
)
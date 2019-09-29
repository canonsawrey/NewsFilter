package com.csawrey.newsstreams.splash

import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.data.room.DatabaseNewsStream
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import java.time.ZonedDateTime

val mockedStreams = listOf(
    DatabaseNewsStream(1, "2020 Election", ZonedDateTime.now().toInstant().toEpochMilli()),
    DatabaseNewsStream(2, "Sports", ZonedDateTime.now().toInstant().toEpochMilli())
)

val mockedSearchItems = listOf(
    DatabaseSearchItem(0, 1.toLong(), "Joe Biden", Sort.RELEVANT.toString(),
        Weight.AVERAGE.toString(), 7, ZonedDateTime.now().toInstant().toEpochMilli()),
    DatabaseSearchItem(0, 1.toLong(), "Donald Trump", Sort.RELEVANT.toString(),
        Weight.AVERAGE.toString(), 7, ZonedDateTime.now().toInstant().toEpochMilli()),
    DatabaseSearchItem(0, 2.toLong(), "Boston Red Sox", Sort.POPULAR.toString(),
        Weight.SMALL.toString(), 3, ZonedDateTime.now().toInstant().toEpochMilli()),
    DatabaseSearchItem(0, 2.toLong(), "New England Patriots", Sort.POPULAR.toString(),
        Weight.LARGE.toString(), 3, ZonedDateTime.now().toInstant().toEpochMilli()),
    DatabaseSearchItem(0, 2.toLong(), "Boston Bruins", Sort.POPULAR.toString(),
        Weight.AVERAGE.toString(), 3, ZonedDateTime.now().toInstant().toEpochMilli())
)
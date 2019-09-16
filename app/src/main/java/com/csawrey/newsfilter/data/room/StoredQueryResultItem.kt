package com.csawrey.newsfilter.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoredQueryResultItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String
)
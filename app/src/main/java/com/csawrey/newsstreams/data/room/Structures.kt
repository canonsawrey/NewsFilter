package com.csawrey.newsstreams.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class DatabaseNewsStream(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "name") val name: String
)

@Entity(foreignKeys = [ForeignKey(entity = DatabaseNewsStream::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("parent_stream"),
    onDelete = ForeignKey.CASCADE) ] )

data class DatabaseSearchItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val parent_stream: Long,
    @ColumnInfo(name = "keyword") val keyword: String
)

@Entity(foreignKeys = [ForeignKey(entity = DatabaseSearchItem::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("parent_search_item"),
    onDelete = ForeignKey.CASCADE) ] )

data class DatabaseCachedStoryItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val parent_search_item: Long,
    @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String
)
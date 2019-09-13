package com.csawrey.newsfilter.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseSearchItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "keyword") val keyword: String,
    @ColumnInfo(name = "feed_name") val feed_name: String
)
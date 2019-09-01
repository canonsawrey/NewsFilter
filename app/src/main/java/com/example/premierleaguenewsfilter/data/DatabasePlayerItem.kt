package com.example.premierleaguenewsfilter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePlayerItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "club") val club: String,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "watched") val watched: Boolean
)
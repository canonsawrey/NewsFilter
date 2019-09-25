package com.csawrey.newsstreams.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsStreamDao {
    @Query("SELECT * FROM DatabaseNewsStream")
    suspend fun getAllStreams(): List<DatabaseNewsStream>

    @Query("SELECT name FROM DatabaseNewsStream WHERE uid = :uid")
    suspend fun getStreamName(uid: Long): String

    @Insert
    fun batchInsert(searches: List<DatabaseNewsStream>)

    @Query("DELETE FROM DatabaseNewsStream")
    fun clearTable()
}
package com.csawrey.newsstreams.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsStreamDao {
    @Query("SELECT * FROM DatabaseNewsStream")
    suspend fun getAllStreams(): List<DatabaseNewsStream>

    @Insert
    fun batchInsert(searches: List<DatabaseNewsStream>)

    @Query("DELETE FROM DatabaseNewsStream")
    fun clearTable()
}
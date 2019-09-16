package com.csawrey.newsfilter.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QueryResultItemsDao {
    @Query("SELECT * FROM StoredQueryResultItem WHERE `query` = :keyword")
    suspend fun getStoredQueryResults(keyword: String): List<StoredQueryResultItem>

    @Insert
    fun batchInsert(searches: List<StoredQueryResultItem>)

    @Query("DELETE FROM DatabaseSearchItem")
    fun clearTable()
}
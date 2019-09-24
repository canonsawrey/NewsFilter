package com.csawrey.newsstreams.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CachedResultItemsDao {
    @Query("SELECT * FROM DatabaseCachedStoryItem WHERE `parent_search_item` = :parentId")
    suspend fun getStoredQueryResults(parentId: Long): List<DatabaseCachedStoryItem>

    @Insert
    fun batchInsert(searches: List<DatabaseCachedStoryItem>)

    @Query("DELETE FROM DatabaseSearchItem")
    fun clearTable()
}
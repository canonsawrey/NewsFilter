package com.csawrey.newsfilter.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchItemsDao {
    @Query("SELECT * FROM DatabaseSearchItem ORDER BY keyword")
    suspend fun getAllSearchItems(): List<DatabaseSearchItem>

    @Insert
    fun batchInsert(searches: List<DatabaseSearchItem>)

    @Query("DELETE FROM DatabaseSearchItem")
    fun clearTable()
}
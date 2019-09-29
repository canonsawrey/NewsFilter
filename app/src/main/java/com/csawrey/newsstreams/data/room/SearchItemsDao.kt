package com.csawrey.newsstreams.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchItemsDao {
    @Query("SELECT * FROM DatabaseSearchItem ORDER BY created")
    suspend fun getAllSearchItems(): List<DatabaseSearchItem>

    @Query("SELECT * FROM DatabaseSearchItem WHERE `parent_stream` = :foreignKey ORDER BY created")
    suspend fun getStreamSearchItems(foreignKey: Long): List<DatabaseSearchItem>

    @Insert
    fun batchInsert(searches: List<DatabaseSearchItem>)

    @Insert
    fun insert(item: DatabaseSearchItem): Long

    @Query("UPDATE DatabaseSearchItem SET keyword = :keyword, sort = :sort, weight = :weight, daysOld = :daysOld WHERE uid = :uid")
    fun update(uid: Long, keyword: String, sort: String, weight: String, daysOld: Int)

    @Query("DELETE FROM DatabaseSearchItem")
    fun clearTable()

    @Query("DELETE FROM DatabaseSearchItem WHERE uid = :uid")
    fun delete(uid: Long)

}
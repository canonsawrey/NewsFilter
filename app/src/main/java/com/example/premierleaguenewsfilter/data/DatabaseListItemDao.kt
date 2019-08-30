package com.example.premierleaguenewsfilter.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseListItemDao {
    @Query("SELECT * FROM DatabaseListItem")
    suspend fun getAllItems(): List<DatabaseListItem>

    @Insert
    suspend fun insert(item: DatabaseListItem): Long

    @Query("DELETE FROM DatabaseListItem WHERE item_name=:name")
    suspend fun remove(name: String)
}
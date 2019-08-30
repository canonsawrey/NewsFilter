package com.example.premierleaguenewsfilter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseListItemDao {
    @Query("SELECT * FROM DatabasePlayerItem")
    suspend fun getAllPlayers(): List<DatabasePlayerItem>

    @Insert
    suspend fun batchInsert(players: List<DatabasePlayerItem>)

    @Query("UPDATE DatabasePlayerItem SET watched = :watched WHERE uid = :id")
    suspend fun setWatched(id: Int, watched: Boolean)
}
package com.example.premierleaguenewsfilter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabasePlayerItemDao {
    @Query("SELECT * FROM DatabasePlayerItem")
    suspend fun getAllPlayers(): List<DatabasePlayerItem>

//    @Query("SELECT * FROM DatabasePlayerItem WHERE position = :position AND club = :club")
//    suspend fun getPlayers(position: String, club: String): List<DatabasePlayerItem>

    @Query("SELECT * FROM DatabasePlayerItem WHERE first_name = :name OR last_name = :name")
    suspend fun getPlayers(name: String)

    @Insert
    fun batchInsert(players: List<DatabasePlayerItem>)

    @Query("UPDATE DatabasePlayerItem SET watched = :watched WHERE uid = :id")
    suspend fun setWatched(id: Int, watched: Boolean)

    @Query("DELETE FROM DatabasePlayerItem")
    fun clearTable()
}
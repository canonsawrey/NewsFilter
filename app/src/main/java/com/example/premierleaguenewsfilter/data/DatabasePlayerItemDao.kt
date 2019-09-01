package com.example.premierleaguenewsfilter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabasePlayerItemDao {
    @Query("SELECT * FROM DatabasePlayerItem ORDER BY last_name")
    suspend fun getAllPlayers(): List<DatabasePlayerItem>

//    @Query("SELECT * FROM DatabasePlayerItem WHERE position = :position AND club = :club")
//    suspend fun getPlayers(position: String, club: String): List<DatabasePlayerItem>

    @Query("SELECT * FROM DatabasePlayerItem WHERE LOWER(first_name) LIKE LOWER(:name) OR LOWER(last_name) LIKE LOWER(:name)")
    suspend fun getPlayers(name: String): List<DatabasePlayerItem>

    @Insert
    fun batchInsert(players: List<DatabasePlayerItem>)

    @Query("UPDATE DatabasePlayerItem SET watched = :watched WHERE uid = :uid")
    suspend fun setWatched(uid: Long, watched: Boolean)

    @Query("DELETE FROM DatabasePlayerItem")
    fun clearTable()

}
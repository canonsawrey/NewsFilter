package com.example.premierleaguenewsfilter.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsFeedsDao {
    @Query("SELECT * FROM NewsFeedDatabaseItem ORDER BY last_name")
    suspend fun getAllPlayers(): List<NewsFeedDatabaseItem>

//    @Query("SELECT * FROM NewsFeedDatabaseItem WHERE position = :position AND club = :club")
//    suspend fun getPlayers(position: String, club: String): List<NewsFeedDatabaseItem>

    @Query("SELECT * FROM NewsFeedDatabaseItem WHERE LOWER(first_name) LIKE LOWER(:name) OR LOWER(last_name) LIKE LOWER(:name)")
    suspend fun getPlayers(name: String): List<NewsFeedDatabaseItem>

    @Insert
    fun batchInsert(newsFeeds: List<NewsFeedDatabaseItem>)

    @Query("UPDATE NewsFeedDatabaseItem SET watched = :watched WHERE uid = :uid")
    suspend fun setWatched(uid: Long, watched: Boolean)

    @Query("DELETE FROM NewsFeedDatabaseItem")
    fun clearTable()

    @Query("SELECT * FROM NewsFeedDatabaseItem WHERE watched = 1")
    suspend fun getWatchedPlayers(): List<NewsFeedDatabaseItem>

}
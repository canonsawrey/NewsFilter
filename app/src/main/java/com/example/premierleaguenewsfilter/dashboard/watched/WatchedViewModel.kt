package com.example.premierleaguenewsfilter.dashboard.watched

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.premierleaguenewsfilter.data.room.AppDatabase
import com.example.premierleaguenewsfilter.data.room.DatabasePlayerItem
import com.example.premierleaguenewsfilter.toSoccerPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WatchedViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _players = MutableLiveData<List<PlayerItem>>()
    val players: LiveData<List<PlayerItem>> = _players

    fun performSearch(query: String) {
        _players.value = null
        viewModelScope.launch {
            val players = async (Dispatchers.IO){
                db.playerDao().getPlayers(query).map { toPlayerItem(it) }
            }
            _players.value = players.await()
        }
    }

    fun getAllPlayers() {
        _players.value = null
        viewModelScope.launch {
            val players = async (Dispatchers.IO){
                db.playerDao().getAllPlayers().map { toPlayerItem(it) }
            }
            _players.value = players.await()
        }
    }

    fun toggleWatched(uid: Long, watched: Boolean) {
        viewModelScope.launch {
            db.playerDao().setWatched(uid, watched)
        }
    }

    private fun toPlayerItem(dbPlayer: DatabasePlayerItem): PlayerItem {
        return PlayerItem(dbPlayer.uid,
            dbPlayer.firstName,
            dbPlayer.lastName,
            dbPlayer.club,
            dbPlayer.position.toSoccerPosition(),
            dbPlayer.watched)
        { uid, tog -> toggleWatched(uid, tog) }
    }
}

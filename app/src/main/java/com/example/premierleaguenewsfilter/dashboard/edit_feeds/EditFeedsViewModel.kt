package com.example.premierleaguenewsfilter.dashboard.edit_feeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.premierleaguenewsfilter.data.room.AppDatabase
import com.example.premierleaguenewsfilter.data.room.NewsFeedDatabaseItem
import com.example.premierleaguenewsfilter.toSoccerPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditFeedsViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _news_feeds = MutableLiveData<List<PlayerItem>>()
    val news_feeds: LiveData<List<PlayerItem>> = _news_feeds

    fun getNewsFeeds() {
        _news_feeds.value = null
        viewModelScope.launch {
            val players = async (Dispatchers.IO){
                db.playerDao().getAllPlayers().map { toPlayerItem(it) }
            }
            _news_feeds.value = players.await()
        }
    }

    fun toggleWatched(uid: Long, watched: Boolean) {
        viewModelScope.launch {
            db.playerDao().setWatched(uid, watched)
        }
    }

    private fun toPlayerItem(dbNewsFeed: NewsFeedDatabaseItem): PlayerItem {
        return PlayerItem(dbNewsFeed.uid,
            dbNewsFeed.firstName,
            dbNewsFeed.lastName,
            dbNewsFeed.club,
            dbNewsFeed.position.toSoccerPosition(),
            dbNewsFeed.watched)
        { uid, tog -> toggleWatched(uid, tog) }
    }
}

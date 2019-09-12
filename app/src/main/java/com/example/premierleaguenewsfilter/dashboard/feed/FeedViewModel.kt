package com.example.premierleaguenewsfilter.dashboard.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.premierleaguenewsfilter.dashboard.watched.PlayerItem
import com.example.premierleaguenewsfilter.data.newsapi.NewsApiRepository
import com.example.premierleaguenewsfilter.data.newsapi.NewsApiService
import com.example.premierleaguenewsfilter.data.room.AppDatabase
import com.example.premierleaguenewsfilter.toPlayerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FeedViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    suspend fun retrieveWatchedPlayers(): List<PlayerItem> {
        return db.playerDao().getWatchedPlayers().map { it.toPlayerItem() }
    }

    fun retrievePlayerNews() {
        viewModelScope.launch {
            val watchedPlayers = async(Dispatchers.IO) {
                retrieveWatchedPlayers()
            }
            val news = async(Dispatchers.IO) {
                //val player1 = watchedPlayers.await()[0]
                //val keyword = "${player1.firstName}%${player1.lastName}"
                val keyword = "France"
                NewsApiRepository.getPlayerNewsRetrofit(keyword)
            }
            val retrievedNews = news.await()
            if (retrievedNews.isSuccessful) {
                Log.d("retrieved_news", retrievedNews.body().toString())
            }
        }
    }
}




package com.csawrey.newsfilter.dashboard.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsfilter.dashboard.edit_feeds.SearchItem
import com.csawrey.newsfilter.data.newsapi.NewsApiRepository
import com.csawrey.newsfilter.data.room.AppDatabase
import com.csawrey.newsfilter.toSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class HomeViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    private suspend fun retrieveSearchItems(): List<SearchItem> {
        return db.searchItemsDao().getAllSearchItems().map { it.toSearchItem() }
    }

    fun retrieveNews() {
        viewModelScope.launch {
            val defSearchItems = async(Dispatchers.IO) { retrieveSearchItems() }

            val searchItems = defSearchItems.await()

            if (searchItems.isEmpty()) {
                _news.value = listOf()
            } else {
                val stringBuilder = StringBuilder()
                stringBuilder.append(searchItems[0].keyword)
                for (i in (1 until searchItems.size)) {
                    stringBuilder.append(" OR ${searchItems[i].keyword}")
                }
                val news = async(Dispatchers.IO) {
                    NewsApiRepository.getNewsRetrofit(stringBuilder.toString())
                }

                val retrievedNews = news.await()
                if (retrievedNews.isSuccessful) {
                    _news.value = retrievedNews.body()?.toNewsItem()
                }
            }
        }
    }
}




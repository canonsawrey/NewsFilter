package com.csawrey.newsstreams.dashboard.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsstreams.dashboard.streams.single_stream.NewsItem
import com.csawrey.newsstreams.data.newsapi.NewsApiRepository
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import com.csawrey.newsstreams.toNewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _searchItems = MutableLiveData<List<NewsItem>>()
    val searchItems: LiveData<List<NewsItem>> = _searchItems

    fun search(keywords: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                NewsApiRepository.getNewsRetrofit(keywords, "relevancy", 7)
            }
            if (results .isSuccessful) {
                _searchItems.value =
                    results.body()!!.toDatabaseCachedStoryItem(0).map { it.toNewsItem() }
            }
        }
    }
}

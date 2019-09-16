package com.csawrey.newsfilter.dashboard.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsfilter.dashboard.edit_feeds.SearchItem
import com.csawrey.newsfilter.data.newsapi.NewsApiRepository
import com.csawrey.newsfilter.data.room.AppDatabase
import com.csawrey.newsfilter.data.room.StoredQueryResultItem
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

    fun receiveNews(list: List<NewsItem>) {
        _news.value = list
    }

//    fun retrieveNews() {
//        viewModelScope.launch {
//            val defSearchItems = async(Dispatchers.IO) { retrieveSearchItems() }
//            val searchItems = defSearchItems.await()
//            FirestoreDatabase.getNews(searchItems[0].keyword, this@HomeViewModel)
//        }
//    }

    fun retrieveNews() {
        viewModelScope.launch {
            val defSearchItems = async(Dispatchers.IO) { retrieveSearchItems() }

            val searchItems = defSearchItems.await()

            if (searchItems.isEmpty()) {
                _news.value = listOf()
            } else {
                var retList: MutableList<NewsItem> = mutableListOf()
                //aggregateStores(searchItems)
                for (searchItem in searchItems) {
                    //Check if the query has been cached yet
                    var storedResults: List<StoredQueryResultItem> = listOf()
                    async (Dispatchers.IO) {
                        storedResults = db.queryResultItemDao().getStoredQueryResults(searchItem.keyword)
                    }

                    if (storedResults.isEmpty()) {
                        val defNews = async(Dispatchers.IO) {
                            NewsApiRepository.getNewsRetrofit(searchItem.keyword)
                        }
                        val news = defNews.await()

                        if (news.isSuccessful) {
                            val body = news.body()
                            if (body != null) {
                                launch (Dispatchers.IO) {
                                    db.queryResultItemDao().batchInsert(
                                        body.toNewsItem().map { it.toStoredQueryResultItem(searchItem.keyword) }
                                    )
                                }
                                retList.addAll(body.toNewsItem())
                            }

                        }
                    }
                }
                _news.value = retList
            }
        }
    }

    private suspend fun aggregateStores(list: List<SearchItem>) {

    }

}




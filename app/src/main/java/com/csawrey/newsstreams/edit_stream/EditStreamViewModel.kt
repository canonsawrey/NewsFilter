package com.csawrey.newsstreams.edit_stream

import android.app.Application
import androidx.lifecycle.*
import com.csawrey.newsstreams.data.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditStreamViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _searchItems = MutableLiveData<List<EditorSearchItem>>()
    val searchItems: LiveData<List<EditorSearchItem>> = _searchItems
    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved


    fun save() {
        _saved.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(1000)
            }
            _saved.value = true
        }
    }

    fun getSearchItems(parentStreamId: Long) {

    }
//    fun retrieveNews(keywords: List<SearchItem>) {
//        val newsList = mutableListOf<NewsItem>()
//
//        viewModelScope.launch {
//            for (keyword in keywords) {
//
//                val stories = withContext(Dispatchers.IO) {
//                    db.cachedResultItemDao().getStoredQueryResults(keyword.uid)
//                }
//
//                if (stories.isEmpty()) {
//                    newsList.addAll(queryApiAndStore(keyword))
//                } else {
//                    newsList.addAll(stories.map { it.toNewsItem() })
//                }
//            }
//            receiveNews(newsList)
//        }
//    }
//
//
//    private fun receiveNews(list: List<NewsItem>) {
//        _news.value = list
//    }
//
//    private suspend fun queryApiAndStore(keyword: SearchItem): List<NewsItem> {
//        val response = withContext(Dispatchers.IO) {
//            NewsApiRepository.getNewsRetrofit(keyword.keyword, keyword.sort.toString(), keyword.daysOld)
//        }
//
//        return if (response.isSuccessful) {
//            val dbItems = response.body()!!.toDatabaseCachedStoryItem(keyword.uid)
//            withContext(Dispatchers.IO) {
//                db.cachedResultItemDao().batchInsert(dbItems)
//            }
//            dbItems.map { it.toNewsItem() }
//        } else {
//            listOf()
//            //TODO implement request not successful handling
//        }
//    }
}

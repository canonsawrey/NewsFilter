package com.csawrey.newsstreams.dashboard.streams

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.toSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StreamsViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val idMap = mutableMapOf<Long, String>()
    private val _streams = MutableLiveData<Map<Long, List<SearchItem>>>()
    val streams: LiveData<Map<Long, List<SearchItem>>> = _streams

    fun retrieveStreams() {
        //Init map
        val streamMap = mutableMapOf<Long, List<SearchItem>>()

        runBlocking {
            viewModelScope.launch(Dispatchers.IO) {
                //Get streams from DB
                val streams = db.newsStreamDao().getAllStreams()
                idMap.clear()
                for (newsStream in streams) {
                    idMap.put(newsStream.uid, newsStream.name)
                }

                for (stream in streams) {
                    val searchTerms = db.searchItemsDao().getStreamSearchItems(stream.uid)
                    streamMap[stream.uid] = searchTerms.map { it.toSearchItem() }
                }
            }
        }
        receiveStreams(streamMap)
    }

    fun receiveStreams(map: Map<Long, List<SearchItem>>) {
        _streams.value = map
    }

//    fun retrieveNews() {
//        viewModelScope.launch {
//            val deferredSearchItems = async(Dispatchers.IO) { retrieveSearchItems() }
//
//            val searchItems = deferredSearchItems.await()
//
//            if (searchItems.isEmpty()) {
//                _news.value = listOf()
//            } else {
//                var retList: MutableList<NewsItem> = mutableListOf()
//                //aggregateStores(searchItems)
//                for (searchItem in searchItems) {
//                    //Check if the query has been cached yet
//                    var storedResults: List<DatabaseCachedStoryItem> = listOf()
//                    async (Dispatchers.IO) {
//                        storedResults = db.queryResultItemDao().getStoredQueryResults(searchItem.keyword)
//                    }
//
//                    if (storedResults.isEmpty()) {
//                        val defNews = async(Dispatchers.IO) {
//                            NewsApiRepository.getNewsRetrofit(searchItem.keyword)
//                        }
//                        val news = defNews.await()
//
//                        if (news.isSuccessful) {
//                            val body = news.body()
//                            if (body != null) {
//                                launch (Dispatchers.IO) {
//                                    db.queryResultItemDao().batchInsert(
//                                        body.toNewsItem().map { it.toStoredQueryResultItem(searchItem.keyword) }
//                                    )
//                                }
//                                retList.addAll(body.toNewsItem())
//                            }
//
//                        }
//                    }
//                }
//                _news.value = retList
//            }
//        }
//    }

    fun streamCount(): Int {
        return _streams.value?.keys?.size ?: 1
    }

    fun getSearchItems(position: Int): List<SearchItem>? {
        return streams.value?.get(getStreamId(position))
    }

    fun getStreamTitle(position: Int): String? {
        return idMap[getStreamId(position)]
    }

    private fun getStreamId(position: Int): Long {
        return idMap.keys.sorted()[position]
    }

}




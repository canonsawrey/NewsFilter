package com.csawrey.newsstreams.dashboard.streams

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.toSearchItem
import kotlinx.coroutines.*

class StreamsViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _streams = MutableLiveData<Map<Int, Triple<Long, String, List<SearchItem>>>>()
    val streams: LiveData<Map<Int, Triple<Long, String, List<SearchItem>>>> = _streams

    fun retrieveStreams() {

        val streamMap = mutableMapOf<Int, Triple<Long, String, List<SearchItem>>>()

        viewModelScope.launch {
            //Get streams from DB
            val streams = withContext(Dispatchers.IO) {
                db.newsStreamDao().getAllStreams()
            }

            for (stream in streams.withIndex()) {
                val searchTerms =  withContext(Dispatchers.IO) {
                    db.searchItemsDao().getStreamSearchItems(stream.value.uid)
                }
                streamMap[stream.index] =
                    Triple(stream.value.uid, stream.value.name, searchTerms.map { it.toSearchItem() })
            }
            receiveStreams(streamMap)
        }
    }


    fun receiveStreams(map: Map<Int, Triple<Long, String, List<SearchItem>>>) {
        _streams.value = map
    }
}




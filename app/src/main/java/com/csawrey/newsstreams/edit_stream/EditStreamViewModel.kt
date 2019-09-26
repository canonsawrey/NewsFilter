package com.csawrey.newsstreams.edit_stream

import android.app.Application
import androidx.lifecycle.*
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.data.room.DatabaseNewsStream
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import com.csawrey.newsstreams.toSort
import com.csawrey.newsstreams.toWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditStreamViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)

    //Only set by an existing stream
    private val _searchItems = MutableLiveData<Pair<String, List<EditorSearchItem>>>()
    val searchItems: LiveData<Pair<String, List<EditorSearchItem>>> = _searchItems

    //Only set by a new stream creating a new stream
    private val _parentId = MutableLiveData<Long>()
    val parentId: LiveData<Long> = _parentId

    fun getSearchItems(parentStreamId: Long) {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) {
                db.searchItemsDao().getStreamSearchItems(parentStreamId)
            }
            val streamName = withContext(Dispatchers.IO) {
                db.newsStreamDao().getStreamName(parentStreamId)
            }
            _searchItems.value = Pair(streamName, items.map { toEditorSearchItem(it) } )
        }
    }

    fun updateStreamName(name: String, streamId: Long, activity: EditStreamActivity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.newsStreamDao().updateStreamName(name, streamId)
            }
            activity.finish()
        }
    }

    fun createStream(name: String) {
        viewModelScope.launch {
            _parentId.value = withContext(Dispatchers.IO) {
                db.newsStreamDao().create(DatabaseNewsStream(0, name))
            }
        }
    }

    fun deleteStream(streamId: Long, activity: EditStreamActivity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.newsStreamDao().delete(streamId)
            }
            activity.finish()
        }
    }

    fun updateSearchItem(uid: Long, keyword: String, sort: String, weight: String, daysOld: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.searchItemsDao().update(uid, keyword, sort, weight, daysOld)
            }
        }
    }

    private fun toEditorSearchItem(it: DatabaseSearchItem) =
        EditorSearchItem(it.uid, it.keyword, it.sort.toSort(), it.weight.toWeight(), it.daysOld,
            updateFunc = {uid: Long, keyword: String, sort: String, weight: String, daysOld: Int ->
                updateSearchItem(uid, keyword, sort, weight, daysOld)
            })
}


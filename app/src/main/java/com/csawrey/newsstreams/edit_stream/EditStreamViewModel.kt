package com.csawrey.newsstreams.edit_stream

import android.app.Application
import androidx.lifecycle.*
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.data.room.DatabaseNewsStream
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import com.csawrey.newsstreams.toSort
import com.csawrey.newsstreams.toWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class EditStreamViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)

    //Only set by an existing stream
    private val _searchItems = MutableLiveData<Pair<String, List<EditorSearchItem>>>()
    val searchItems: LiveData<Pair<String, List<EditorSearchItem>>> = _searchItems

    //Only set by a new stream creating a new stream
    private val _parentId = MutableLiveData<Long>()
    val parentId: LiveData<Long> = _parentId

    //Only set when a new item is created
    private val _new = MutableLiveData<DatabaseSearchItem>()
    val new: LiveData<DatabaseSearchItem> = _new

    private var streamId: Long = 0

    fun getSearchItems(parentStreamId: Long) {
        streamId = parentStreamId
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
                db.newsStreamDao().create(DatabaseNewsStream(0, name, ZonedDateTime.now().toInstant().toEpochMilli()))
            }
            streamId = _parentId.value!!
        }
    }

    fun createItem(parentStreamId: Long) {
        viewModelScope.launch {
            _new.value = withContext(Dispatchers.IO) {
                val id = db.searchItemsDao().insert(
                    DatabaseSearchItem(
                        0,
                        parentStreamId,
                        EditStreamActivity.NEW_PARAM_NAME,
                        Sort.RELEVANT.toString(),
                        Weight.AVERAGE.toString(),
                        3,
                        ZonedDateTime.now().toInstant().toEpochMilli())
                )
                db.searchItemsDao().getSearchItem(id)
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

    fun updateSearchItem(uid: Long, keyword: String, sort: String, weight: String, daysOld: Int, parentStreamId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.searchItemsDao().update(uid, keyword, sort, weight, daysOld)
                db.cachedResultItemDao().removeStaleStories(uid)
            }
        }
    }

    private fun toEditorSearchItem(it: DatabaseSearchItem) =
        EditorSearchItem(it.uid, it.keyword, it.sort.toSort(), it.weight.toWeight(), it.daysOld, parentId = it.parent_stream,
            updateFunc = {uid: Long, keyword: String, sort: String, weight: String, daysOld: Int, parentId: Long ->
                updateSearchItem(uid, keyword, sort, weight, daysOld, parentId)
            })

    fun deleteItem(uid: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.searchItemsDao().delete(uid)
            }
        }
    }
}


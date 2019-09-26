package com.csawrey.newsstreams.edit_stream

import android.app.Application
import androidx.lifecycle.*
import com.csawrey.newsstreams.dashboard.search.toEditorSearchItem
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditStreamViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _searchItems = MutableLiveData<Pair<String, List<EditorSearchItem>>>()
    val searchItems: LiveData<Pair<String, List<EditorSearchItem>>> = _searchItems
    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun updateTitle()

    fun getSearchItems(parentStreamId: Long) {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) {
                db.searchItemsDao().getStreamSearchItems(parentStreamId)
            }
            val streamName = withContext(Dispatchers.IO) {
                db.newsStreamDao().getStreamName(parentStreamId)
            }
            _searchItems.value = Pair(streamName, items.map { it.toEditorSearchItem() } )
        }
    }
}

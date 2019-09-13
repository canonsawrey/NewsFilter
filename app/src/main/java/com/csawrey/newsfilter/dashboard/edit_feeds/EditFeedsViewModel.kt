package com.csawrey.newsfilter.dashboard.edit_feeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsfilter.data.room.AppDatabase
import com.csawrey.newsfilter.data.room.DatabaseSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditFeedsViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _searchItems = MutableLiveData<List<SearchItem>>()
    val searchItems: LiveData<List<SearchItem>> = _searchItems

    fun getAllSearchItems() {
        _searchItems.value = null
        viewModelScope.launch {
            val items = async (Dispatchers.IO){
                db.searchItemsDao().getAllSearchItems().map { toSearchItem(it) }
            }
            _searchItems.value = items.await()
        }
    }

    private fun toSearchItem(dbSearch: DatabaseSearchItem): SearchItem {
        return SearchItem(
            dbSearch.uid,
            dbSearch.keyword)
    }
}

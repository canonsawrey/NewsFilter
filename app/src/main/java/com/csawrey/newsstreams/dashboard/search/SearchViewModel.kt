package com.csawrey.newsstreams.dashboard.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _searchItems = MutableLiveData<List<SearchItem>>()
    val searchItems: LiveData<List<SearchItem>> = _searchItems

    fun getAllSearchItems() {
        _searchItems.value = null
    }
}

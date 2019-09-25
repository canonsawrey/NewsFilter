package com.csawrey.newsstreams.dashboard.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.toSearchItem
import kotlinx.coroutines.*

class SettingsViewModel(private val app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _loaded = MutableLiveData<Boolean>()
    val loaded: LiveData<Boolean> = _loaded
    var successMessage = ""

    fun clearCache() {
        successMessage = app.baseContext.resources.getString(R.string.clearing_cache)
        _loaded.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.cachedResultItemDao().clearTable()
                //Delay so UI is less jittery to the user
                delay(1000)
            }
            _loaded.value = true
        }
    }
}




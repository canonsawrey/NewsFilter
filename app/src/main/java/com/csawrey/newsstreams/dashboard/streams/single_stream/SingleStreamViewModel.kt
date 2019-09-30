package com.csawrey.newsstreams.dashboard.streams.single_stream

import android.app.Application
import androidx.lifecycle.*
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.data.newsapi.NewsApiRepository
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.toNewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleStreamViewModel(app: Application): AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app.applicationContext)
    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    fun retrieveNews(searchItems: List<SearchItem>) {
        val buildList = mutableListOf<Pair<Weight, List<NewsItem>>>()

        viewModelScope.launch {
            for (searchItem in searchItems) {

                val stories = withContext(Dispatchers.IO) {
                    db.cachedResultItemDao().getStoredQueryResults(searchItem.uid)
                }

                if (stories.isEmpty()) {
                    buildList.add(Pair(searchItem.weight, queryApiAndStore(searchItem)))
                } else {
                    buildList.add(Pair(searchItem.weight, stories.map { it.toNewsItem() }))
                }
            }
            buildNews(buildList)
        }
    }


    private fun buildNews(buildList: List<Pair<Weight, List<NewsItem>>>) {
        val newsList = mutableListOf<NewsItem>()

        for (i in 0..5) {
            for (pair in buildList) {
                val storyCount = pair.first.toStoryCount()
                for (j in 0.until(storyCount)) {
                    if (pair.second.size > i * storyCount + j) {
                        newsList.add(pair.second[i * storyCount + j])
                    }
                }
            }
        }

        _news.value = newsList
    }

    private suspend fun queryApiAndStore(keyword: SearchItem): List<NewsItem> {
        val response = withContext(Dispatchers.IO) {
            NewsApiRepository.getNewsRetrofit(keyword.keyword, keyword.sort.toString(), keyword.daysOld)
        }

        return if (response.isSuccessful) {
            val dbItems = response.body()!!.toDatabaseCachedStoryItem(keyword.uid)
            withContext(Dispatchers.IO) {
                db.cachedResultItemDao().batchInsert(dbItems)
            }
            dbItems.map { it.toNewsItem() }
        } else {
            listOf()
            //TODO implement request not successful handling
        }
    }
}

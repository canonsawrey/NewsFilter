package com.csawrey.newsfilter.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csawrey.newsfilter.R
import com.csawrey.newsfilter.dashboard.DashboardActivity
import com.csawrey.newsfilter.data.room.AppDatabase
import com.csawrey.newsfilter.mock.mockedSearchItems
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val disp = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        loadSharedPreferences()
        loadPremierLeaguePlayers()
        nextStep()
    }

    private fun loadPremierLeaguePlayers() {
        val db = AppDatabase.getInstance(application)
        GlobalScope.launch {
            db.searchItemsDao().clearTable()
            db.searchItemsDao().batchInsert(mockedSearchItems.map { it.toDatabaseSearchItem() })
        }
    }

    private fun nextStep() {
        disp.add(Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe {
            DashboardActivity.launch(this, applicationContext)
        })
    }

    private fun loadSharedPreferences() {
        //getSharedPreferences(Util.PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        disp.dispose()
    }
}


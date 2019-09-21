package com.csawrey.newsstreams.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.dashboard.DashboardActivity
import com.csawrey.newsstreams.data.room.AppDatabase
import com.csawrey.newsstreams.mock.mockedSearchItems
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.text.Html
import com.csawrey.newsstreams.mock.mockedStreams
import kotlinx.android.synthetic.main.activity_splash.*



class SplashActivity : AppCompatActivity() {

    private val disp = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        val text = "<font color=#ffffff>News</font>" +
                "<font color=#1976d2>S</font>" +
                "<font color=#ffffff>treams</font>"
        title_text.text = Html.fromHtml(text)
        loadSharedPreferences()
        loadDatabase()
        nextStep()
    }

    private fun loadDatabase() {
        val db = AppDatabase.getInstance(application)
        GlobalScope.launch {
            db.newsStreamDao().clearTable()
            db.searchItemsDao().clearTable()
            db.newsStreamDao().batchInsert(mockedStreams)
            db.searchItemsDao().batchInsert(mockedSearchItems.map { it.toDatabaseSearchItem(1) })
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


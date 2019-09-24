package com.csawrey.newsstreams.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.dashboard.DashboardActivity
import com.csawrey.newsstreams.data.room.AppDatabase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.text.Html
import com.csawrey.newsstreams.app.App
import kotlinx.android.synthetic.main.activity_splash.*



class SplashActivity : AppCompatActivity() {

    private val disp = CompositeDisposable()
    private lateinit var sharedPreferences: SharedPreferences

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
        if (sharedPreferences.getBoolean("firstLaunch", true)) {
            populateStreams()
            sharedPreferences.edit().putBoolean("firstLaunch", false).apply()
        }
        nextStep()
    }

    private fun populateStreams() {
        val db = AppDatabase.getInstance(application)
        GlobalScope.launch {
            db.newsStreamDao().clearTable()
            db.searchItemsDao().clearTable()
            db.newsStreamDao().batchInsert(mockedStreams)
            db.searchItemsDao().batchInsert(mockedSearchItems)
        }
    }

    private fun nextStep() {
        disp.add(Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe {
            DashboardActivity.launch(this, applicationContext)
        })
    }

    private fun loadSharedPreferences() {
        sharedPreferences = getSharedPreferences(App.preferences, Context.MODE_PRIVATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        disp.dispose()
    }
}


package com.example.premierleaguenewsfilter.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.dashboard.DashboardActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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


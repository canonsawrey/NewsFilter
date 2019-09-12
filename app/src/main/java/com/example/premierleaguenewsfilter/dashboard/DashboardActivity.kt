package com.example.premierleaguenewsfilter.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.dashboard.home.HomeFragment
import com.example.premierleaguenewsfilter.dashboard.preferences.PreferencesFragment
import com.example.premierleaguenewsfilter.dashboard.edit_feeds.EditFeedsFragment
import com.pandora.bottomnavigator.BottomNavigator

class DashboardActivity : AppCompatActivity() {

    private lateinit var navigator: BottomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setupBottomNavigator()
    }

    private fun setupBottomNavigator() {
        navigator = BottomNavigator.onCreate(
            fragmentContainer = R.id.fragment_container,
            bottomNavigationView = findViewById(R.id.bottom_nav_view),
            rootFragmentsFactory = mapOf(
                R.id.home to { HomeFragment() },
                R.id.editsFeeds to { EditFeedsFragment() },
                R.id.preferences to { PreferencesFragment() }
            ),
            defaultTab = R.id.home,
            activity = this
        )
    }

    override fun onBackPressed() {
        if (!navigator.pop()) {
            super.onBackPressed()
        }
    }

    companion object {

        fun launch(activity: Activity, context: Context) {
            val bundle = ActivityOptionsCompat.makeCustomAnimation(context,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
            val intent = Intent(activity, DashboardActivity::class.java)
            activity.startActivity(intent, bundle)
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}

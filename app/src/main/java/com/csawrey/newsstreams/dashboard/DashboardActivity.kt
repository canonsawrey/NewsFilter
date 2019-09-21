package com.csawrey.newsstreams.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.csawrey.newsstreams.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private var currentTab: BottomNavTab? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        determineCurrentTab(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupBottomNavigation()
    }

    private fun determineCurrentTab(savedInstanceState: Bundle?) {
        val id = savedInstanceState?.getInt(EXTRA_SELECTED_TAB)
            ?: intent.getIntExtra(EXTRA_SELECTED_TAB, R.id.nav_streams)

        currentTab = BottomNavTab.fromId(id)
    }

    private fun setupBottomNavigation() {
        bottom_nav.setOnNavigationItemSelectedListener { item ->
            val tab = BottomNavTab.fromId(item.itemId)
            selectTab(tab)
            true
        }

        bottom_nav.selectedItemId = currentTab?.id ?: R.id.nav_streams
    }

    private fun selectTab(tab: BottomNavTab) {
        val fragment = supportFragmentManager.findFragmentByTag(tab.toString())
            ?: tab.newFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tab.toString())
            .commit()
    }

    enum class BottomNavTab(@IdRes val id: Int, val newFragment: () -> Fragment) {
        STREAMS(R.id.nav_streams, { com.csawrey.newsstreams.dashboard.streams.StreamsFragment() }),
        SEARCH(R.id.nav_search, { com.csawrey.newsstreams.dashboard.search.SearchFragment() }),
        SETTINGS(
            R.id.nav_settings,
            { com.csawrey.newsstreams.dashboard.settings.SettingsFragment() });

        companion object {
            fun fromId(@IdRes id: Int): BottomNavTab {
                for (tab in values()) {
                    if (id == tab.id) {
                        return tab
                    }
                }

                throw IllegalArgumentException("No tab with id of $id found.")
            }
        }
    }

    companion object {
        const val EXTRA_SELECTED_TAB = "extra:selected_tab"

        fun launch(activity: Activity, context: Context) {
            val bundle = ActivityOptionsCompat.makeCustomAnimation(context,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
            val intent = Intent(activity, DashboardActivity::class.java)
            activity.startActivity(intent, bundle)
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}

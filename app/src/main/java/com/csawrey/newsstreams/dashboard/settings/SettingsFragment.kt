package com.csawrey.newsstreams.dashboard.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.app.App
import com.csawrey.newsstreams.common.BaseAdapter
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private val settingsAdapter = BaseAdapter<SettingsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupRecycler()
        getSettings()
    }

    private fun setupRecycler() {
        settings_recycler.layoutManager = LinearLayoutManager(context)
        settings_recycler.adapter = settingsAdapter
    }

    private fun getSettings() {
        val list = mutableListOf<SettingsItem>()

        list.add(SettingsItem("App Version", App.build))

        settingsAdapter.submitList(list)
    }
}
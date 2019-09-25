package com.csawrey.newsstreams.dashboard.settings

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.app.App
import com.csawrey.newsstreams.common.BaseAdapter
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private val settingsAdapter = BaseAdapter<SettingsItem>()
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loaded.observe(viewLifecycleOwner, Observer { showAnimation(it) })
        setupRecycler()
        setupButtonListeners()
        getSettings()
    }

    private fun showAnimation(it: Boolean) {
        loading.visibility = if (it) View.INVISIBLE else View.VISIBLE
        clear_cache.visibility = if (it) View.VISIBLE else View.INVISIBLE
    }

    private fun setupButtonListeners() {
        clear_cache.setOnClickListener {
            viewModel.clearCache()
        }
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
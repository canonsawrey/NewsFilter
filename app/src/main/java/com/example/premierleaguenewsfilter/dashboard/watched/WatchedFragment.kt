package com.example.premierleaguenewsfilter.dashboard.watched

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseAdapter
import com.example.premierleaguenewsfilter.data.AppDatabase
import com.example.premierleaguenewsfilter.mock.mockedPlayers
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_watched.*
import javax.inject.Inject

class WatchedFragment : Fragment() {

    //@Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WatchedViewModel
    private val adapter = BaseAdapter<PlayerItem>()
    private val db = AppDatabase.getInstance(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProviders.of(this, factory)[WatchedViewModel::class.java]
        viewModel = ViewModelProviders.of(this)[WatchedViewModel::class.java]
    }

    private fun setupRecycler() {
        watched_recycler.layoutManager = LinearLayoutManager(requireContext())
        watched_recycler.adapter = adapter
        adapter.submitList(mockedPlayers)
        watched_recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watched, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupToolAndSearchBar()
    }

    private fun setupToolAndSearchBar() {
        search_bar.visibility = View.INVISIBLE
        search.setOnClickListener {
            toolbar.visibility = View.INVISIBLE
            search_bar.visibility = View.VISIBLE
            search_bar.isIconified = false
            search_bar.requestFocus()
            //bottom_nav_view.visibility = View.GONE
        }
        search_bar.setOnCloseListener {
            toolbar.visibility = View.VISIBLE
            search_bar.visibility = View.INVISIBLE
            //bottom_nav_view.visibility = View.VISIBLE
            true
        }
        search_bar.setOnSearchClickListener {
            db.playerDao().getPlayers(search_bar.query)
        }
    }
}
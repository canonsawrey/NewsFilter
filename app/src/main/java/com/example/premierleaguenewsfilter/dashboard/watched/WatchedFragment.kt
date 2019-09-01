package com.example.premierleaguenewsfilter.dashboard.watched

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseAdapter
import com.example.premierleaguenewsfilter.dashboard.DashboardActivity
import com.example.premierleaguenewsfilter.data.AppDatabase
import com.example.premierleaguenewsfilter.data.DatabasePlayerItem
import com.example.premierleaguenewsfilter.mock.mockedPlayers
import com.example.premierleaguenewsfilter.toSoccerPosition
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_watched.*
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WatchedFragment : Fragment() {

    //@Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WatchedViewModel
    private val adapter = BaseAdapter<PlayerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProviders.of(this, factory)[WatchedViewModel::class.java]
        viewModel = WatchedViewModel(requireActivity().application)
        //ViewModelProviders.of(this)[WatchedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watched, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.players.observe(viewLifecycleOwner, Observer { receiveList(it) })
        viewModel.getAllPlayers()
        setupRecycler()
        setupToolAndSearchBar()
    }

    private fun receiveList(list: List<PlayerItem>?) {
        when (list) {
            null -> showLoadingState()
            else -> {
                if (list.isEmpty()) {
                    showEmptyState()
                } else {
                    adapter.submitList(list)
                    Observable.timer(400, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                        showPlayers()
                    }
                }
            }
        }
    }

    private fun showPlayers() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        loading_state.visibility = View.INVISIBLE
        watched_recycler.visibility = View.VISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun showEmptyState() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        loading_state.visibility = View.INVISIBLE
        watched_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        loading_state.visibility = View.VISIBLE
        watched_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun setupRecycler() {
        watched_recycler.layoutManager = LinearLayoutManager(requireContext())
        watched_recycler.adapter = adapter
        watched_recycler.itemAnimator = null
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
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.performSearch("%${search_bar.query}%")
                return false
            } }
        )
    }

    companion object {
        val fadeOutTransition = Fade(Fade.OUT)

        init {
            //fadeOutTransition.duration = 2000
        }
    }
}
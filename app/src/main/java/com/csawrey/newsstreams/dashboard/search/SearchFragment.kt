package com.csawrey.newsstreams.dashboard.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.recycler.BaseAdapter
import com.csawrey.newsstreams.dashboard.streams.single_stream.NewsItem
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    //@Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    private val adapter = BaseAdapter<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchItems.observe(viewLifecycleOwner, Observer { receiveList(it) })
        search_bar.setOnQueryTextListener(SearchListener())
        showEmptyState()
        setupRecycler()
    }

    private fun receiveList(list: List<NewsItem>) {
        if (list.isEmpty()) {
            showEmptyState()
        } else {
            adapter.submitList(list)
            showNews()
        }
    }

    private fun showNews() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        shimmer.visibility = View.INVISIBLE
        news_recycler.visibility = View.VISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun showEmptyState() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        shimmer.visibility = View.INVISIBLE
        news_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        shimmer.visibility = View.VISIBLE
        news_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun setupRecycler() {
        news_recycler.layoutManager = LinearLayoutManager(requireContext())
        news_recycler.adapter = adapter
    }

    companion object {
        val fadeOutTransition = Fade(Fade.OUT)
    }

    inner class SearchListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                viewModel.search(it)
                showLoadingState()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean { return true }
    }
}
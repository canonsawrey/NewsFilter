package com.csawrey.newsstreams.dashboard.streams.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseAdapter
import com.csawrey.newsstreams.dashboard.search.SearchFragment.Companion.fadeOutTransition
import com.csawrey.newsstreams.dashboard.search.SearchItem
import kotlinx.android.synthetic.main.fragment_single_stream.*

class SingleStreamFragment(title: String, keywords: List<SearchItem>) : Fragment() {
    private lateinit var viewModel: SingleStreamViewModel
    private val newsAdapter = BaseAdapter<NewsItem>()
    private val keywordAdapter = BaseAdapter<SearchItem>()

    init {
        stream_title.text = title
        keywordAdapter.submitList(keywords)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[SingleStreamViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_stream, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.news.observe(viewLifecycleOwner, Observer { receiveNewsList(it) })
//        viewModel.nullSearch() //.retrieveNews()
        setupRecyclers()
        showNews()
    }

    private fun setupRecyclers() {
        keyword_recycler.adapter = keywordAdapter
    }

    private fun receiveNewsList(list: List<NewsItem>?) {
        when (list) {
            null -> showLoadingState()
            else -> {
                if (list.isEmpty()) {
                    showEmptyState()
                } else {
//                    adapter.submitList(list)
                    showNews()
                }
            }
        }
    }

    private fun showNews() {
//        TransitionManager.beginDelayedTransition(conta, fadeOutTransition)
//        loading_state.visibility = View.INVISIBLE
//        feed_recycler.visibility = View.VISIBLE
//        empty_state.visibility = View.INVISIBLE
    }

    private fun showEmptyState() {
//        feed_recycler.visibility = View.INVISIBLE
//        loading_state.visibility = View.INVISIBLE
//        empty_state.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
//        feed_recycler.visibility = View.INVISIBLE
//        loading_state.visibility = View.VISIBLE
//        empty_state.visibility = View.INVISIBLE
    }
}
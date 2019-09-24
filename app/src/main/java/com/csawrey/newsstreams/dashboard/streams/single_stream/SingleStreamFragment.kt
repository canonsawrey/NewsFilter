package com.csawrey.newsstreams.dashboard.streams.single_stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseAdapter
import com.csawrey.newsstreams.dashboard.search.SearchItem
import kotlinx.android.synthetic.main.fragment_single_stream.*

class SingleStreamFragment(
    private val title: String,
    private val keywords: List<SearchItem>
) : Fragment() {
    private lateinit var viewModel: SingleStreamViewModel
    private val newsAdapter = BaseAdapter<NewsItem>()
    private val keywordAdapter = BaseAdapter<SearchItem>()

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
        viewModel.news.observe(viewLifecycleOwner, Observer { receiveNewsList(it) })
        viewModel.retrieveNews(keywords)
        stream_title.text = title
        showLoadingState()
        setupRecyclers()
    }

    private fun setupRecyclers() {
        keyword_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        keyword_recycler.adapter = keywordAdapter
        keywordAdapter.submitList(keywords)

        news_recycler.layoutManager = LinearLayoutManager(context)
        news_recycler.adapter = newsAdapter
    }

    private fun receiveNewsList(list: List<NewsItem>) {
        if (list.isEmpty()) {
            //TODO Implement empty list messaging asking user to broaden search terms
        } else {
            newsAdapter.submitList(list)
            showNews()
        }

    }

    private fun showNews() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        shimmer.visibility = View.INVISIBLE
        shimmer.stopShimmer()
        news_recycler.visibility = View.VISIBLE

    }

    private fun showLoadingState() {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
        news_recycler.visibility = View.INVISIBLE
    }

    companion object {
        val fadeOutTransition = Fade(Fade.OUT)
    }
}
package com.csawrey.newsstreams.dashboard.streams.single_stream

import android.content.Intent
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
import com.csawrey.newsstreams.common.recycler.BaseAdapter
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.edit_stream.EditStreamActivity
import kotlinx.android.synthetic.main.fragment_single_stream.*

class SingleStreamFragment(
    private val uid: Long,
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
        setupClickListeners()
    }

    private fun setupClickListeners() {
        header.setOnClickListener {
            EditStreamActivity.launchExisting(requireActivity(), REQUEST_CODE, uid)
        }
    }

    private fun setupRecyclers() {
        keyword_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        keyword_recycler.adapter = keywordAdapter
        if (keywords.isEmpty()) {
            keywordAdapter.submitList(listOf(
                SearchItem(0, requireContext().resources.getString(R.string.none),
                    Sort.POPULAR, Weight.AVERAGE, 0))
            )
            receiveNewsList(listOf())
        } else {
            keywordAdapter.submitList(keywords)
        }

        news_recycler.layoutManager = LinearLayoutManager(context)
        news_recycler.adapter = newsAdapter
    }

    private fun receiveNewsList(list: List<NewsItem>) {
        if (list.isEmpty()) {
            showEmptyState()
        } else {
            newsAdapter.submitList(list)
            showNews()
        }
    }

    private fun showEmptyState() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        empty_state.visibility = View.VISIBLE
        shimmer.visibility = View.INVISIBLE
        shimmer.stopShimmer()
        news_recycler.visibility = View.INVISIBLE
    }

    private fun showNews() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        empty_state.visibility = View.INVISIBLE
        shimmer.visibility = View.INVISIBLE
        shimmer.stopShimmer()
        news_recycler.visibility = View.VISIBLE

    }

    private fun showLoadingState() {
        empty_state.visibility = View.INVISIBLE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
        news_recycler.visibility = View.INVISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == EditStreamActivity.STREAM_CHANGED) {
            data?.getStringExtra(EditStreamActivity.TITLE)?.let {
                stream_title.text = it
            }
        }
    }

    companion object {
        //launchEditForResult
        const val REQUEST_CODE = 9001

        val fadeOutTransition = Fade(Fade.OUT)
    }
}
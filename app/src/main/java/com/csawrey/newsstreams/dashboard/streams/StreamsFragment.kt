package com.csawrey.newsstreams.dashboard.streams

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.dashboard.streams.single_stream.NewStreamFragment
import com.csawrey.newsstreams.dashboard.streams.single_stream.SingleStreamFragment
import kotlinx.android.synthetic.main.fragment_streams.*
import java.lang.Exception

class StreamsFragment : Fragment() {

    private lateinit var viewModel: StreamsViewModel
    private lateinit var viewPagerAdapter: StreamPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[StreamsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_streams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingState()
        viewModel.streams.observe(viewLifecycleOwner, Observer { receiveNewsList(it) })
        viewModel.retrieveStreams()
    }

    private fun setupViewPager(map: Map<Int, Triple<Long, String, List<SearchItem>>>) {
        viewPagerAdapter = StreamPagerAdapter(requireActivity().supportFragmentManager, map)
        stream_pager.adapter = viewPagerAdapter
    }

    private fun receiveNewsList(map: Map<Int, Triple<Long, String, List<SearchItem>>>) {
//        if (map.keys.isNotEmpty()) {
//
//        }
        setupViewPager(map)
        showNews()
    }

    private fun showLoadingState() {
        placeholder.visibility = View.VISIBLE
        stream_pager.visibility = View.INVISIBLE
    }

    private fun showNews() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        placeholder.visibility = View.INVISIBLE
        stream_pager.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        showLoadingState()
        viewModel.retrieveStreams()
    }

    private inner class StreamPagerAdapter(fm: FragmentManager, map: Map<Int, Triple<Long, String, List<SearchItem>>>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        val mMap = map

        override fun getCount() = mMap.keys.size + 1

        override fun getItem(position: Int): Fragment {
            return if (position == count - 1) {
                NewStreamFragment()
            } else {
                SingleStreamFragment(
                    mMap[position]!!.first,
                    mMap[position]!!.second,
                    mMap[position]!!.third)
            }
        }
    }

    companion object {
        val fadeOutTransition = Fade(Fade.OUT)
    }
}
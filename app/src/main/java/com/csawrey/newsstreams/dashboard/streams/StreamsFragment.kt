package com.csawrey.newsstreams.dashboard.streams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.dashboard.streams.single_stream.NewStreamFragment
import com.csawrey.newsstreams.dashboard.streams.single_stream.SingleStreamFragment
import kotlinx.android.synthetic.main.fragment_streams.*

class StreamsFragment : Fragment() {

    private lateinit var viewModel: StreamsViewModel
    private lateinit var viewPagerAdapter: FragmentStatePagerAdapter

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveStreams()
    }

    private fun setupViewPager(map: Map<Int, Pair<String, List<SearchItem>>>) {
        viewPagerAdapter = StreamPagerAdapter(requireActivity().supportFragmentManager, map)
        stream_pager.adapter = viewPagerAdapter
    }

    private fun receiveNewsList(map: Map<Int, Pair<String, List<SearchItem>>>) {
        if (map.keys.isNotEmpty()) {
            setupViewPager(map)
            showNews()
        }
    }

    private fun showLoadingState() {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
        stream_pager.visibility = View.INVISIBLE
    }

    private fun showNews() {
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        stream_pager.visibility = View.VISIBLE
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class StreamPagerAdapter(fm: FragmentManager, map: Map<Int, Pair<String, List<SearchItem>>>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        val mMap = map

        override fun getCount() = mMap.keys.size + 1

        override fun getItem(position: Int): Fragment {
            return if (position == count - 1) {
                NewStreamFragment()
            } else {
                SingleStreamFragment(mMap[position]!!.first,
                    mMap[position]!!.second)
            }
        }
    }
}
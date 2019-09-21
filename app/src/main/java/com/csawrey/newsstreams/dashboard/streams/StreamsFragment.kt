package com.csawrey.newsstreams.dashboard.streams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import androidx.viewpager.widget.ViewPager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseAdapter
import com.csawrey.newsstreams.dashboard.search.SearchFragment.Companion.fadeOutTransition
import com.csawrey.newsstreams.dashboard.search.SearchItem
import com.csawrey.newsstreams.dashboard.streams.stream.NewStreamFragment
import com.csawrey.newsstreams.dashboard.streams.stream.NewsItem
import com.csawrey.newsstreams.dashboard.streams.stream.SingleStreamFragment
import kotlinx.android.synthetic.main.fragment_streams.*

class StreamsFragment : Fragment() {

    private lateinit var viewModel: StreamsViewModel
    private lateinit var viewPager: ViewPager
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
        viewModel.streams.observe(viewLifecycleOwner, Observer { receiveNewsList(it) })
        viewModel.retrieveStreams()
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager = stream_pager
        viewPagerAdapter = StreamPagerAdapter(requireActivity().supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
    }

    private fun receiveNewsList(map: Map<Long, List<SearchItem>>?) {
        when (map) {
            null -> showLoadingState()
            else -> {
                if (map.keys.isEmpty()) {
                    Toast.makeText(context, "Add a stream!", Toast.LENGTH_LONG).show()
                } else {
                    ////////
                }
            }
        }
    }

    private fun showLoadingState() {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
        stream_pager.visibility = View.INVISIBLE
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class StreamPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = viewModel.streamCount()

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                NewStreamFragment()
            } else {
                SingleStreamFragment(viewModel.getStreamTitle(position)!!,
                    viewModel.getSearchItems(position)!!)
            }
        }
    }
}
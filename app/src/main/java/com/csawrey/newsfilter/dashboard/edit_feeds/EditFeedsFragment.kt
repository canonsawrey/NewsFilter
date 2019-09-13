package com.csawrey.newsfilter.dashboard.edit_feeds

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
import com.csawrey.newsfilter.R
import com.csawrey.newsfilter.common.BaseAdapter
import kotlinx.android.synthetic.main.fragment_watched.*

class EditFeedsFragment : Fragment() {

    //@Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: EditFeedsViewModel
    private val adapter = BaseAdapter<SearchItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[EditFeedsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watched, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchItems.observe(viewLifecycleOwner, Observer { receiveList(it) })
        viewModel.getAllSearchItems()
        setupRecycler()
    }

    private fun receiveList(list: List<SearchItem>?) {
        when (list) {
            null -> showLoadingState()
            else -> {
                if (list.isEmpty()) {
                    showEmptyState()
                } else {
                    adapter.submitList(list)
                    showPlayers()
                }
            }
        }
    }

    private fun showPlayers() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        loading_state.visibility = View.INVISIBLE
        feed_selector_recycler.visibility = View.VISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun showEmptyState() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        loading_state.visibility = View.INVISIBLE
        feed_selector_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        loading_state.visibility = View.VISIBLE
        feed_selector_recycler.visibility = View.INVISIBLE
        empty_state.visibility = View.INVISIBLE
    }

    private fun setupRecycler() {
        feed_selector_recycler.layoutManager = LinearLayoutManager(requireContext())
        feed_selector_recycler.adapter = adapter
    }

    companion object {
        val fadeOutTransition = Fade(Fade.OUT)

        init {
            //fadeOutTransition.duration = 2000
        }
    }
}
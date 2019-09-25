package com.csawrey.newsstreams.dashboard.streams.single_stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.edit_stream.EditStreamActivity
import kotlinx.android.synthetic.main.fragment_new_stream.*

class NewStreamFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_stream_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add.setOnClickListener {
            EditStreamActivity.launch(requireActivity())
        }
    }
}
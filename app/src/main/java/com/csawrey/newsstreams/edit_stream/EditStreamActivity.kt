package com.csawrey.newsstreams.edit_stream

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseAdapter
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.activity_edit_stream.*


class EditStreamActivity : AppCompatActivity() {
    private var parentStreamId: Long? = null
    private lateinit var viewModel: EditStreamViewModel
    private val adapter = BaseAdapter<EditorItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_stream)
        viewModel = ViewModelProviders.of(this)[EditStreamViewModel::class.java]
        if (intent.getBooleanExtra(FROM_EXISTING_STREAM, false)) {
            parentStreamId = intent.getLongExtra(STREAM_UID, 0)
            viewModel.getSearchItems(parentStreamId!!)
            showLoading()
        } else {
            displayItems(Pair(baseContext.resources.getString(R.string.my_new_stream), listOf()))
            parentStreamId = 0
        }
        setupToolbar()
        setupRecycler()
        setupListeners()
        setupObservers()
    }

    private fun showEditor() {
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        parameter_recycler.visibility = View.VISIBLE
    }

    private fun showLoading() {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        parameter_recycler.visibility = View.INVISIBLE
    }

    private fun setupObservers() {
        viewModel.searchItems.observe(this, Observer { displayItems(it) })
    }

    private fun displayItems(data: Pair<String, List<EditorSearchItem>>) {
        if (data.second.isEmpty()) {
            adapter.submitList(listOf(
                EditorSearchItem(),
                AddItem { addRow() }
            ))
        } else {
            val tempList: MutableList<EditorItem> = data.second.toMutableList()
            tempList.add(AddItem { addRow() })
            adapter.submitList(tempList)
        }
        stream_name_value.setText(data.first)
        showEditor()
    }

    private fun addRow() {
        val list = adapter.currentList.toMutableList()
        list.add(list.size - 1, EditorSearchItem())
        adapter.submitList(list)
    }

    private fun setupListeners() {
        toolbar.setNavigationOnClickListener {
            backPressed()
        }
        stream_name_value.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                stream_name_value.error = null
            }
        }
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(baseContext)
        parameter_recycler.layoutManager = layoutManager
        parameter_recycler.adapter = adapter
    }

    override fun onBackPressed() {
        backPressed()
    }

    private fun backPressed() {
        if (stream_name_value.text.isNullOrBlank()) {
            Toast.makeText(baseContext, "Cannot have an empty stream name", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.updateStreamName()
        }
    }

    companion object {
        //Launch extras
        const val FROM_EXISTING_STREAM = "from_existing_stream"
        const val STREAM_UID = "stream_uid"

        //Result codes
        const val STREAM_CHANGED = 1001

        //Result extras
        const val TITLE = "title"

        fun launch(activity: Activity) {
            val intent = Intent(activity, EditStreamActivity::class.java)
            activity.startActivity(intent)
        }

        fun launchForResult(activity: Activity, requestCode: Int, streamId: Long) {
            val intent = Intent(activity, EditStreamActivity::class.java)
            intent.putExtra(FROM_EXISTING_STREAM, true)
            intent.putExtra(STREAM_UID, streamId)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}

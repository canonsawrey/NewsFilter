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
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.dashboard.search.SearchItem
import kotlinx.android.synthetic.main.activity_edit_stream.*

class EditStreamActivity : AppCompatActivity() {
    private var parentStreamId: Long? = null
    private lateinit var viewModel: EditStreamViewModel
    private val adapter = BaseAdapter<EditorItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_stream)
        viewModel = ViewModelProviders.of(this)[EditStreamViewModel::class.java]
        setupToolbar()
        setupRecycler()
        setupListeners()
        setupObservers()
        if (intent.getBooleanExtra(FROM_EXISTING_STREAM, false)) {
            parentStreamId = intent.getLongExtra(STREAM_UID, 0)
            viewModel.getSearchItems(parentStreamId!!)
            showLoading()
        } else {
            displayItems(Pair(baseContext.resources.getString(R.string.my_new_stream), listOf()))
        }
    }

    private fun showEditor() {
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        editor.visibility = View.VISIBLE
    }

    private fun showLoading() {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        editor.visibility = View.INVISIBLE
    }

    private fun setupObservers() {
        viewModel.saved.observe(this, Observer { showAnimation(it) })
        viewModel.searchItems.observe(this, Observer { displayItems(it) })
    }

    private fun displayItems(data: Pair<String, List<EditorSearchItem>>) {
        if (data.second.isEmpty()) {
            adapter.submitList(listOf(
                EditorSearchItem(0, "", Sort.RELEVANT, Weight.AVERAGE, 3),
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
        Toast.makeText(baseContext, "Adding row! But not really...", Toast.LENGTH_SHORT).show()
    }

    private fun setupListeners() {
        save.setOnClickListener {
            if (stream_name_value.text.toString().isBlank()) {
                stream_name_value.error = resources.getString(R.string.empty_name_error)
            } else {
                viewModel.save()
            }
        }
        stream_name_value.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                stream_name_value.error = null
            }
        }
    }

    private fun setupRecycler() {
        parameter_recycler.layoutManager = LinearLayoutManager(baseContext)
        parameter_recycler.adapter = adapter
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showAnimation(it: Boolean) {
        loading.visibility = if (it) View.INVISIBLE else View.VISIBLE
        save.visibility = if (it) View.VISIBLE else View.INVISIBLE
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

package com.csawrey.newsstreams.edit_stream

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseAdapter
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.activity_edit_stream.*
import kotlinx.android.synthetic.main.activity_edit_stream.container
import kotlinx.android.synthetic.main.activity_edit_stream.shimmer


class EditStreamActivity : AppCompatActivity() {
    private var parentStreamId: Long? = null
    private lateinit var viewModel: EditStreamViewModel
    private val adapter = BaseAdapter<EditorItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_stream)
        viewModel = ViewModelProviders.of(this)[EditStreamViewModel::class.java]
        setupObservers()
        if (intent.getBooleanExtra(FROM_EXISTING_STREAM, false)) {
            parentStreamId = intent.getLongExtra(STREAM_UID, 0)
            viewModel.getSearchItems(parentStreamId!!)
            showLoading()
        } else {
            displayItems(Pair(baseContext.resources.getString(R.string.my_new_stream), listOf()))
            viewModel.createStream(NEW_STREAM_NAME)
            stream_name_value.setText(NEW_STREAM_NAME)
            showLoading()
        }
        setupRecycler()
        setupListeners()
    }

    private fun showEditor() {
        TransitionManager.beginDelayedTransition(container, fadeOutTransition)
        shimmer.stopShimmer()
        shimmer.visibility = View.INVISIBLE
        info_container.visibility = View.VISIBLE
    }

    private fun showLoading() {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        info_container.visibility = View.INVISIBLE
    }

    private fun setupObservers() {
        viewModel.parentId.observe(this, Observer {
            parentStreamId = it
            adapter.submitList(listOf(AddItem { viewModel.createItem(parentStreamId!!) }))
            showEditor()
        })
        viewModel.searchItems.observe(this, Observer { displayItems(it) })
        viewModel.new.observe(this, Observer { acceptCreatedItem(it) })
    }

    private fun displayItems(data: Pair<String, List<EditorSearchItem>>) {
        val tempList: MutableList<EditorItem> = data.second.toMutableList()
        tempList.add(AddItem { viewModel.createItem(parentStreamId!!) })
        adapter.submitList(tempList)
        stream_name_value.setText(data.first)
        showEditor()
    }

    private fun acceptCreatedItem(it: DatabaseSearchItem) {
        val list = adapter.currentList.toMutableList()
        list.add(it.toEditorItem())
        adapter.submitList(list)
    }

    private fun setupListeners() {
        nav_back.setOnClickListener {
            backPressed()
        }
        delete.setOnClickListener {
            showDeleteDialog()
        }
        stream_name_value.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                stream_name_value.error = null
            }
        }
        info.setOnClickListener {
            launchInfoDialog()
        }
    }

    private fun launchInfoDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val imgView = ImageView(baseContext)
        dialog.setTitle(resources.getString(R.string.key))
        imgView.setImageResource(R.drawable.editor_key)
        dialog.setView(imgView)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.ok)) { dialogInterface, _ -> dialogInterface.cancel() }
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.colorAccentBlue))
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
            stream_name_value.error = resources.getString(R.string.cannot_be_blank)
        } else {
            viewModel.updateStreamName(stream_name_value.text.toString(), parentStreamId!!, this)
        }
    }

    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle(resources.getString(R.string.delete_stream))
        dialog.setMessage(resources.getString(R.string.delete_stream_dialog_message))
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.no)) { dialogInterface, _ -> dialogInterface.cancel() }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.yes)) { _, _ -> deleteStream() }
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.colorAccentBlue))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.red))
    }

    private fun deleteStream() {
        viewModel.deleteStream(parentStreamId!!, this)
    }

    companion object {
        //Launch extras
        const val FROM_EXISTING_STREAM = "from_existing_stream"
        const val STREAM_UID = "stream_uid"

        //Result codes
        const val STREAM_CHANGED = 1001

        //Result extras
        const val TITLE = "title"

        //General constants
        const val NEW_STREAM_NAME = "My News Stream"
        const val NEW_PARAM_NAME = "New Term"

        fun launch(activity: Activity) {
            val intent = Intent(activity, EditStreamActivity::class.java)
            activity.startActivity(intent)
        }

        fun launchExisting(activity: Activity, requestCode: Int, streamId: Long) {
            val intent = Intent(activity, EditStreamActivity::class.java)
            intent.putExtra(FROM_EXISTING_STREAM, true)
            intent.putExtra(STREAM_UID, streamId)
            activity.startActivityForResult(intent, requestCode)
        }

        val fadeOutTransition = Fade(Fade.OUT)
    }
}

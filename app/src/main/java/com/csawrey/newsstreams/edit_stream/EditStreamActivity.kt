package com.csawrey.newsstreams.edit_stream

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.csawrey.newsstreams.R
import kotlinx.android.synthetic.main.activity_edit_stream.*

class EditStreamActivity : AppCompatActivity() {
    private lateinit var viewModel: EditStreamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_stream)
        viewModel = ViewModelProviders.of(this)[EditStreamViewModel::class.java]
        setupToolbar()
        setupClickListener()
        viewModel.saved.observe(this, Observer { showAnimation(it) })
    }

    private fun setupClickListener() {
        save.setOnClickListener {
            viewModel.save()
        }
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
            intent.putExtra(EditStreamActivity.STREAM_UID, streamId)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}

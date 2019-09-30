package com.csawrey.newsstreams.common.recycler

import com.csawrey.newsstreams.edit_stream.EditStreamViewModel
import com.csawrey.newsstreams.edit_stream.EditorSearchItem

class SwipeToDeleteAdapter(private val viewModel: EditStreamViewModel) : BaseAdapter<EditorSearchItem>() {

    fun deleteItem(adapterPosition: Int) {
        val list = currentList.toMutableList()
        val item = list.removeAt(adapterPosition)
        viewModel.deleteItem(item.getId())
        submitList(list)
    }
}
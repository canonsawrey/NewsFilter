package com.csawrey.newsstreams.edit_stream

import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseViewHolder
import com.csawrey.newsstreams.common.Item
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import kotlinx.android.synthetic.main.editor_search_item.view.*

interface EditorItem : Item

data class EditorSearchItem (
    private val uid: Long = 0,
    private val keyword: String,
    private val sort: Sort,
    private val weight: Weight,
    private val daysOld: Int
) : EditorItem {
    override fun layoutId(): Int = R.layout.editor_search_item

    override fun uniqueId(): Long = uid

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.stream_name_value.setText(keyword)
    }
}

class AddItem(
    private val func: () -> Unit
) : EditorItem {
    override fun layoutId(): Int  = R.layout.editor_add_item

    override fun uniqueId(): Long = -1

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.setOnClickListener {
            func.invoke()
        }
    }

}
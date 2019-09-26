package com.csawrey.newsstreams.edit_stream

import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseViewHolder
import com.csawrey.newsstreams.common.Item
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.editor_add_item.view.*
import kotlinx.android.synthetic.main.editor_search_item.view.*
import java.time.ZonedDateTime

interface EditorItem : Item {
    fun toDatabaseItem(parentId: Long): DatabaseSearchItem?
}

data class EditorSearchItem (
    private val uid: Long = 0,
    private var keyword: String = "",
    var sort: Sort = Sort.RELEVANT,
    var weight: Weight = Weight.AVERAGE,
    var daysOld: Int = 3,
    private val created: Long = ZonedDateTime.now().toInstant().toEpochMilli()
) : EditorItem {

    override fun toDatabaseItem(parentId: Long): DatabaseSearchItem? {
        return if (keyword.isBlank()) {
            null
        } else {
            DatabaseSearchItem(uid, parentId, keyword, sort.toString(), weight.toString(), daysOld)
        }
    }

    override fun layoutId(): Int = R.layout.editor_search_item

    override fun uniqueId(): Long = created + uid.hashCode()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.stream_name_value.text = keyword
        holder.itemView.sort_ic.setImageResource(sort.toDrawableResource())
        holder.itemView.weight_ic.setImageResource(weight.toDrawableResource())
        holder.itemView.days_old_value.text = daysOld.toString()
    }
}

class AddItem (
    private val func: () -> Unit
) : EditorItem {

    override fun toDatabaseItem(parentId: Long): DatabaseSearchItem? = null

    override fun layoutId(): Int  = R.layout.editor_add_item

    override fun uniqueId(): Long = -1

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.add_button.setOnClickListener {
            func.invoke()
        }
    }
}

private fun Sort.toPosition(): Int {
    return when (this) {
        Sort.RELEVANT -> 0
        Sort.RECENT -> 1
        Sort.POPULAR -> 2
    }
}

private fun Sort.toDrawableResource(): Int {
    return when (this) {
        Sort.RELEVANT -> R.drawable.ic_search_black
        Sort.RECENT -> R.drawable.ic_clock_black
        Sort.POPULAR -> R.drawable.ic_trending_up_black
    }
}

private fun Weight.toPosition(): Int {
    return when (this) {
        Weight.SMALL -> 0
        Weight.AVERAGE -> 1
        Weight.LARGE -> 2
    }
}

private fun Weight.toDrawableResource(): Int {
    return when (this) {
        Weight.SMALL -> R.drawable.ic_low_blue
        Weight.AVERAGE -> R.drawable.ic_neutral_black
        Weight.LARGE -> R.drawable.ic_high_red
    }
}

private fun Int.toPosition(): Int {
    return when (this) {
        1 -> 0
        2 -> 1
        3 -> 2
        5 -> 3
        7 -> 4
        14 -> 5
        else -> throw IllegalArgumentException("Unrecognized daysOld value: $this")
    }
}

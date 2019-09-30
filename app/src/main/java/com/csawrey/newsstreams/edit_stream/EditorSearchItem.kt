package com.csawrey.newsstreams.edit_stream

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.setPadding
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.recycler.BaseViewHolder
import com.csawrey.newsstreams.common.recycler.Item
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.editor_add_item.view.*
import kotlinx.android.synthetic.main.editor_search_item.view.*
import kotlinx.android.synthetic.main.editor_search_item.view.stream_name_value
import java.time.ZonedDateTime

data class EditorSearchItem (
    private val uid: Long = 0,
    private var keyword: String = "",
    private var sort: Sort = Sort.RELEVANT,
    private var weight: Weight = Weight.AVERAGE,
    private var daysOld: Int = 3,
    private val created: Long = ZonedDateTime.now().toInstant().toEpochMilli(),
    private val parentId: Long,
    private var updateFunc: ((Long, String, String, String, Int, Long) -> Unit)?
) : Item {

    fun toDatabaseItem(parentId: Long): DatabaseSearchItem? {
        return if (keyword.isBlank()) {
            null
        } else {
            DatabaseSearchItem(uid, parentId, keyword, sort.toString(), weight.toString(),
                daysOld, ZonedDateTime.now().toInstant().toEpochMilli())
        }
    }

    fun giveUpdateFunc(func: ((Long, String, String, String, Int, Long) -> Unit)) {
        updateFunc = func
    }

    fun getId(): Long = uid

    override fun layoutId(): Int = R.layout.editor_search_item

    override fun uniqueId(): Long = created + uid.hashCode()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.stream_name_value.text = keyword
        holder.itemView.sort_ic.setImageResource(sort.toDrawableResource())
        holder.itemView.weight_ic.setImageResource(weight.toDrawableResource())
        holder.itemView.days_old_value.text = daysOld.toString()
        setupClickListeners(holder)
    }

    private fun setupClickListeners(holder: BaseViewHolder) {
        holder.itemView.sort_ic.setOnClickListener {
            sort = sort.toNextSort()
            holder.itemView.sort_ic.setImageResource(sort.toDrawableResource())
            updateFunc!!.invoke(uid, keyword, sort.toString(), weight.toString(), daysOld, parentId)
        }
        holder.itemView.weight_ic.setOnClickListener {
            weight = weight.toNextWeight()
            holder.itemView.weight_ic.setImageResource(weight.toDrawableResource())
            updateFunc!!.invoke(uid, keyword, sort.toString(), weight.toString(), daysOld, parentId)
        }
        holder.itemView.days_old_value.setOnClickListener {
            daysOld = daysOld.toNextDaysOld()
            holder.itemView.days_old_value.text = daysOld.toString()
            updateFunc!!.invoke(uid, keyword, sort.toString(), weight.toString(), daysOld, parentId)
        }
        holder.itemView.stream_name_value.setOnClickListener {
            launchKeywordDialog(holder.itemView.context, holder.itemView.stream_name_value)
        }
    }

    private fun launchKeywordDialog(context: Context, textView: TextView) {
        val dialog = AlertDialog.Builder(context).create()
        dialog.setTitle(context.resources.getString(R.string.edit_keyword))
        val input = EditText(context)
        input.setText(keyword)
        dialog.setView(input)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.resources.getString(R.string.cancel))
            { dialogInterface, _ -> dialogInterface.cancel() }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.resources.getString(R.string.save))
            { _, _ ->
                keyword = input.text.toString()
                textView.text = keyword
                updateFunc!!.invoke(uid, keyword, sort.toString(), weight.toString(), daysOld, parentId)
            }
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(context.resources.getColor(R.color.red))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(context.resources.getColor(R.color.colorAccentBlue))
    }
}

private fun Sort.toNextSort(): Sort {
    return when (this) {
        Sort.RELEVANT -> Sort.RECENT
        Sort.RECENT -> Sort.POPULAR
        Sort.POPULAR -> Sort.RELEVANT
    }
}

private fun Weight.toNextWeight(): Weight {
    return when (this) {
        Weight.SMALL -> Weight.AVERAGE
        Weight.AVERAGE -> Weight.LARGE
        Weight.LARGE -> Weight.SMALL
    }
}

private fun Int.toNextDaysOld(): Int {
    return when (this) {
        1 -> 2
        2 -> 3
        3 -> 5
        5 -> 7
        7 -> 14
        14 -> 1
        else -> throw IllegalArgumentException("Unrecognized daysOld value: $this")
    }
}

private fun Sort.toDrawableResource(): Int {
    return when (this) {
        Sort.RELEVANT -> R.drawable.ic_search_black
        Sort.RECENT -> R.drawable.ic_clock_black
        Sort.POPULAR -> R.drawable.ic_trending_up_black
    }
}

private fun Weight.toDrawableResource(): Int {
    return when (this) {
        Weight.SMALL -> R.drawable.ic_low_blue
        Weight.AVERAGE -> R.drawable.ic_neutral_black
        Weight.LARGE -> R.drawable.ic_high_red
    }
}
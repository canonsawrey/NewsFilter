package com.csawrey.newsstreams.dashboard.settings

import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.recycler.BaseViewHolder
import com.csawrey.newsstreams.common.recycler.Item
import kotlinx.android.synthetic.main.settings_item.view.*

data class SettingsItem(
    val label: String,
    val value: String
) : Item {
    override fun layoutId() = R.layout.settings_item

    override fun uniqueId(): Long = (label.hashCode() + value.hashCode()).toLong()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.item_label.text = label
        holder.itemView.item_value.text = value
    }

}

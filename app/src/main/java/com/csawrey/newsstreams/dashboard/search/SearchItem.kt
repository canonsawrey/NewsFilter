package com.csawrey.newsstreams.dashboard.search

import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseViewHolder
import com.csawrey.newsstreams.common.Item
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.search_item.view.*

data class SearchItem(
    val uid: Long = 0,
    val keyword: String
) : Item {
    override fun layoutId() = R.layout.search_item

    override fun uniqueId() = (keyword.hashCode() + uid.hashCode()).toLong()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.keyword.text = keyword
    }

    fun toDatabaseSearchItem(foreignKey: Long): DatabaseSearchItem {

        return DatabaseSearchItem(
            uid,
            foreignKey,
            "main"
        )
    }
}

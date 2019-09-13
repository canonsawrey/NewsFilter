package com.csawrey.newsfilter.dashboard.edit_feeds

import com.csawrey.newsfilter.R
import com.csawrey.newsfilter.common.BaseViewHolder
import com.csawrey.newsfilter.common.Item
import com.csawrey.newsfilter.data.room.DatabaseSearchItem
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

    fun toDatabaseSearchItem(): DatabaseSearchItem {

        return DatabaseSearchItem(
            uid,
            keyword,
            "main"
        )
    }
}

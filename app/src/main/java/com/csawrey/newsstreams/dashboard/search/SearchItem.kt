package com.csawrey.newsstreams.dashboard.search

import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseViewHolder
import com.csawrey.newsstreams.common.Item
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.common.Weight
import com.csawrey.newsstreams.data.room.DatabaseSearchItem
import kotlinx.android.synthetic.main.search_item.view.*

data class SearchItem(
    val uid: Long = 0,
    val keyword: String,
    val sort: Sort,
    val weight: Weight,
    val daysOld: Int
) : Item {
    override fun layoutId() = R.layout.search_item

    override fun uniqueId() = uid

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.keyword.text = keyword
    }
}

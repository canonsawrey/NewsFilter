package com.example.premierleaguenewsfilter.dashboard.edit_feeds

import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseViewHolder
import com.example.premierleaguenewsfilter.common.Item

data class EditFeedSelectorItem(
    val title: String
) : Item {
    override fun layoutId() = R.layout.edit_feed_selector_item

    override fun uniqueId() = title.hashCode().toLong()

    override fun bind(holder: BaseViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

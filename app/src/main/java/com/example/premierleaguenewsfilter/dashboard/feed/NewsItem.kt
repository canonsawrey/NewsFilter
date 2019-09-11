package com.example.premierleaguenewsfilter.dashboard.feed

import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseViewHolder
import com.example.premierleaguenewsfilter.common.Item
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItem (
    private val headline: String,
    private val source: String
) : Item {
    override fun layoutId() = R.layout.news_item

    override fun uniqueId() = (headline.hashCode() + source.hashCode()).toLong()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.news_text.text = headline

        holder.itemView.image.setImageResource(R.color.colorPrimaryDark)
    }
}

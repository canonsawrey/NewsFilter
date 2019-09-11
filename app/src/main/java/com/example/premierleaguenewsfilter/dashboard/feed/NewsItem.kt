package com.example.premierleaguenewsfilter.dashboard.feed

import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseViewHolder
import com.example.premierleaguenewsfilter.common.Item
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItem (
    private val uid: Long,
    private val headline: String,
    private val source: NewsSource
) : Item {
    override fun layoutId() = R.layout.news_item

    override fun uniqueId() = uid

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.news_text.text = headline
        when (source) {
            NewsSource.TWITTER -> holder.itemView.image.setImageResource(R.color.colorAccent)
            else -> holder.itemView.image.setImageResource(R.drawable.border_grey)
        }
        holder.itemView.image.setColorFilter(source.toColor())
    }

}

enum class NewsSource {
    TWITTER;

    fun toColor(): Int {
        when (this) {
            TWITTER -> return R.color.twitter
        }
    }
}

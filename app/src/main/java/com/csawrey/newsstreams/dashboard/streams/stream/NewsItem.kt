package com.csawrey.newsstreams.dashboard.streams.stream

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.BaseViewHolder
import com.csawrey.newsstreams.common.Item
import com.csawrey.newsstreams.data.room.DatabaseCachedStoryItem
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItem (private val author: String?,
    private val title: String,
    private val description: String,
    private val url: String
) : Item {
    override fun layoutId() = R.layout.news_item

    override fun uniqueId() = description.hashCode().toLong()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.news_text.text = title
        holder.itemView.image.setImageResource(R.color.colorAccent)
        holder.itemView.setOnClickListener { openArticle(holder.itemView.context) }
    }

    private fun openArticle(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

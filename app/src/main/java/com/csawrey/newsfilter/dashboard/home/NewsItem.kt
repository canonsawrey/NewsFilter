package com.csawrey.newsfilter.dashboard.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.csawrey.newsfilter.R
import com.csawrey.newsfilter.common.BaseViewHolder
import com.csawrey.newsfilter.common.Item
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

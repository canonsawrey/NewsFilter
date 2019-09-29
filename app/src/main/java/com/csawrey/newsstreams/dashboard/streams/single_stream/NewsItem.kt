package com.csawrey.newsstreams.dashboard.streams.single_stream

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.csawrey.newsstreams.R
import com.csawrey.newsstreams.common.recycler.BaseViewHolder
import com.csawrey.newsstreams.common.recycler.Item
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItem (
    private val author: String?,
    private val title: String,
    private val description: String,
    private val url: String,
    private val imageUrl: String
) : Item {
    override fun layoutId() = R.layout.news_item

    override fun uniqueId() = description.hashCode().toLong()

    override fun bind(holder: BaseViewHolder) {
        holder.itemView.news_text.text = title
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.news_image_placeholder)
            .centerCrop()
            .into(holder.itemView.image)
        holder.itemView.read_more.setOnClickListener { openArticle(holder.itemView.context) }
        if (author.isNullOrBlank()) {
            holder.itemView.author.isVisible = false
        } else {
            holder.itemView.author.text = author
        }
    }

    private fun openArticle(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

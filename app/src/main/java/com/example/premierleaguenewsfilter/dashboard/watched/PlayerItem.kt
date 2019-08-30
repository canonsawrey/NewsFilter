package com.example.premierleaguenewsfilter.dashboard.watched

import com.example.premierleaguenewsfilter.R
import com.example.premierleaguenewsfilter.common.BaseViewHolder
import com.example.premierleaguenewsfilter.common.Item
import com.example.premierleaguenewsfilter.data.AppDatabase
import kotlinx.android.synthetic.main.player_item.view.*

data class PlayerItem(
    val uid: Int,
    val firstName: String,
    val lastName: String,
    val club: String,
    val position: SoccerPosition,
    var watched: Boolean = false
) : Item {

    private lateinit var mHolder: BaseViewHolder
    lateinit var db: AppDatabase

    override fun layoutId(): Int = R.layout.player_item

    override fun uniqueId(): Long {
        TODO("not implemented")
    }

    override fun bind(holder: BaseViewHolder) {
        mHolder = holder
        val formattedName = "$lastName, $firstName"
        holder.itemView.name.text = formattedName
        holder.itemView.position.text = position.toString()
        holder.itemView.club.text = club
        val bg: Int
        if (watched) {
            bg = R.drawable.background_player_selected
            holder.itemView.watched_checkbox.isChecked = true
        } else {
            bg = R.drawable.background_player_unselected
            holder.itemView.watched_checkbox.isChecked = false
        }
        holder.itemView.player_card.setBackgroundResource(bg)

        holder.itemView.watched_checkbox.setOnClickListener {
            toggleWatched()
        }
    }

    private fun toggleWatched() {
        watched = !watched
        if (watched) {
            mHolder.itemView.player_card.setBackgroundResource(
                R.drawable.background_player_selected)
            mHolder.itemView.watched_checkbox.isChecked = true
        } else {
            mHolder.itemView.player_card.setBackgroundResource(
                R.drawable.background_player_unselected)
            mHolder.itemView.watched_checkbox.isChecked = false
        }

    }

}

package com.example.premierleaguenewsfilter

import com.example.premierleaguenewsfilter.dashboard.edit_feeds.PlayerItem
import com.example.premierleaguenewsfilter.dashboard.edit_feeds.SoccerPosition
import com.example.premierleaguenewsfilter.data.room.NewsFeedDatabaseItem

fun PlayerItem.toDatabasePlayerItem(): NewsFeedDatabaseItem {
    return NewsFeedDatabaseItem(
        this.uid,
        this.firstName,
        this.lastName,
        this.club,
        this.position.toString(),
        this.watched
    )
}

fun NewsFeedDatabaseItem.toPlayerItem(): PlayerItem {
    return PlayerItem(this.uid,
        this.firstName,
        this.lastName,
        this.club,
        this.position.toSoccerPosition(),
        this.watched)
}

fun String.toSoccerPosition(): SoccerPosition {
    return when (this) {
        "FWD" -> SoccerPosition.FWD
        "Forward" -> SoccerPosition.FWD
        "MID" -> SoccerPosition.MID
        "Midfielder" -> SoccerPosition.MID
        "DEF" -> SoccerPosition.DEF
        "Defender" -> SoccerPosition.DEF
        "GK" -> SoccerPosition.GK
        "Goalkeeper" -> SoccerPosition.GK
        else -> SoccerPosition.UNKNOWN
    }

    TODO("Add 'powered by newsapi' or w.e.t.f. they wanted")
    TODO("Hide API key")
}

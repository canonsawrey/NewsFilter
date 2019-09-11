package com.example.premierleaguenewsfilter

import com.example.premierleaguenewsfilter.dashboard.watched.PlayerItem
import com.example.premierleaguenewsfilter.dashboard.watched.SoccerPosition
import com.example.premierleaguenewsfilter.data.room.DatabasePlayerItem

fun PlayerItem.toDatabasePlayerItem(): DatabasePlayerItem {
    return DatabasePlayerItem(
        this.uid,
        this.firstName,
        this.lastName,
        this.club,
        this.position.toString(),
        this.watched
    )
}

fun DatabasePlayerItem.toPlayerItem(): PlayerItem {
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
}
package com.example.premierleaguenewsfilter

import com.example.premierleaguenewsfilter.dashboard.watched.PlayerItem
import com.example.premierleaguenewsfilter.dashboard.watched.SoccerPosition
import com.example.premierleaguenewsfilter.data.DatabasePlayerItem
import java.lang.IllegalArgumentException

fun PlayerItem.toDatabasePlayerItem(): DatabasePlayerItem {
    return DatabasePlayerItem(0,
        this.firstName,
        this.lastName,
        this.club,
        this.position.toString(),
        this.watched)
}

fun String.toSoccerPosition(): SoccerPosition {
    when (this) {
        "FWD" -> SoccerPosition.FWD
        "Forward" -> SoccerPosition.FWD
        "MID" -> SoccerPosition.MID
        "Midfielder" -> SoccerPosition.MID
        "DEF" -> SoccerPosition.DEF
        "Defender" -> SoccerPosition.DEF
        "GK" -> SoccerPosition.GK
        "Goalkeeper" -> SoccerPosition.GK
    }
    throw IllegalArgumentException("Not a recognized position: $this")
}
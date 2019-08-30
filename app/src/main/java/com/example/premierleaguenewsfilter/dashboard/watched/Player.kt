package com.example.premierleaguenewsfilter.dashboard.watched

data class Player(
    val firstName: String,
    val lastName: String,
    val club: String,
    val position: SoccerPosition
)

enum class SoccerPosition {
    FWD, MID, DEF, GK
}

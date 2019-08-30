package com.example.premierleaguenewsfilter.dashboard.watched

enum class SoccerPosition {
    FWD, MID, DEF, GK;

    override fun toString(): String {
        return when (this) {
            FWD -> "Forward"
            MID -> "Midfielder"
            DEF -> "Defender"
            GK -> "Goalkeeper"
        }
    }
}

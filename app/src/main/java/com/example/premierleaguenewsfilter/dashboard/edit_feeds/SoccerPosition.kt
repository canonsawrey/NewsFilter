package com.example.premierleaguenewsfilter.dashboard.edit_feeds

@Deprecated("No longer used with generic app")
enum class SoccerPosition {
    FWD, MID, DEF, GK, UNKNOWN;

    override fun toString(): String {
        return when (this) {
            FWD -> "Forward"
            MID -> "Midfielder"
            DEF -> "Defender"
            GK -> "Goalkeeper"
            UNKNOWN -> "Unknown"
        }
    }
}

package com.example.premierleaguenewsfilter.dashboard.watched

import com.example.premierleaguenewsfilter.common.State

sealed class WatchedState: State() {
    object Loading: WatchedState()
    data class PlayersRetrieved(val players: List<PlayerItem>): WatchedState()
    object NoMatchingPlayers: WatchedState()
}
package com.example.premierleaguenewsfilter.dashboard.watched

import com.example.premierleaguenewsfilter.common.Action

sealed class WatchedAction: Action() {
    data class PlayerSearch(val search: String): WatchedAction()
}
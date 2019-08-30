package com.example.premierleaguenewsfilter.dashboard.watched

class WatchedReducer {
    fun reduce(action: Action, state: State): State {
        return when (action) {
            is Action.PlayerSearch -> State.Loading
        }
    }
}
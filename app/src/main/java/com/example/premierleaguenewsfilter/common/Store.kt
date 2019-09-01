package com.example.premierleaguenewsfilter.common

class Store<A: Action, S : State>(
    initialState: S,
    private val reducer: Reducer<A, S>
) {
    private var stateListener: ((S) -> Unit)? = null

    private var currentState: S = initialState
        set(value) {
            field = value
            stateListener?.invoke(value)
        }

    fun dispatch(action: A) {
        currentState = reducer.reduce(action, currentState)
    }

    fun subscribe(stateListener: ((S) -> Unit)?) {
        this.stateListener = stateListener
    }
}
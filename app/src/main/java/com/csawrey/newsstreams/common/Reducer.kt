package com.csawrey.newsstreams.common

abstract class Reducer<A: Action, S: State> {
    abstract fun reduce(action: A, state: S): S
}
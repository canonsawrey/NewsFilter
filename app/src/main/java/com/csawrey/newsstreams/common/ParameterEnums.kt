package com.csawrey.newsstreams.common

enum class Sort {
    RELEVANT, POPULAR, RECENT;

    override fun toString(): String {
        return when (this) {
            RELEVANT -> "relevancy"
            POPULAR -> "popularity"
            RECENT -> "publishedAt"
        }
    }
}

enum class Weight {
    SMALL, AVERAGE, LARGE;

    override fun toString(): String {
        return when (this) {
            SMALL -> "small"
            AVERAGE -> "average"
            LARGE -> "large"
        }
    }
}
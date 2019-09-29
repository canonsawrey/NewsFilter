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

    fun fromString(str: String): Sort {
        return when (str) {
            "relevancy" -> RELEVANT
            "popularity" -> POPULAR
            "publishedAt" -> RECENT
            else -> throw IllegalArgumentException("Unrecognized sort: $str")
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

    fun fromString(str: String): Weight {
        return when (str) {
            "small" -> SMALL
            "average" -> AVERAGE
            "large" -> LARGE
            else -> throw IllegalArgumentException("Unrecognized weight: $str")
        }
    }
}
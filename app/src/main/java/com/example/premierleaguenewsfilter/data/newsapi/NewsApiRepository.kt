package com.example.premierleaguenewsfilter.data.newsapi

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object NewsApiRepository {
    var api: NewsApiService  = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(NewsApiService::class.java)

    suspend fun getPlayerNewsRetrofit(keywords: String): Response<NewsApiItem> =
        api.getPlayerStories(
            keywords,
            "6743c75732f145538c1d4c0750a7e5cd",
            "en",
            daysAgo(2),
            daysAgo(0),
            "relevancy")

    private fun daysAgo(daysAgo: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return formattedDate(cal)
    }

    private fun formattedDate(cal: Calendar): String {
        return "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}"
    }
}
package com.example.premierleaguenewsfilter.data.newsapi

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiRepository {
    var api: NewsApiService  = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/top-headlines/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(NewsApiService::class.java)

    suspend fun getPlayerNewsRetrofit(keywords: String): Response<NewsApiItem> =
        api.getPlayerStories(keywords, "6743c75732f145538c1d4c0750a7e5cd")
}
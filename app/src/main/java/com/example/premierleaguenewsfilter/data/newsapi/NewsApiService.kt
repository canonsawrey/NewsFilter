package com.example.premierleaguenewsfilter.data.newsapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {
    @GET("v2/everything?q={name}&apiKey=6743c75732f145538c1d4c0750a7e5cd")
    fun getPlayerStories(@Path(value = "name") playerName: String): Call<NewsApiItem>
}
package com.example.premierleaguenewsfilter.data.newsapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET suspend fun getPlayerStories(
        @Query("q") searchKeyword: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsApiItem>

//    @GET("q={keyword}&apiKey={apiKey}")
//    suspend fun getPlayerStories(
//        keyword: String,
//        apiKey: String
//    ): Response<NewsApiItem>
}
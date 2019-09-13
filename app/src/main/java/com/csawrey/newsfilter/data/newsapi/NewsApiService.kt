package com.csawrey.newsfilter.data.newsapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET ("everything")
    suspend fun getStories(
        @Query("q") searchKeyword: String,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sort: String,
        @Query("pageSize") pageSize: String
    ): Response<NewsApiItem>

//    @GET("q={keyword}&apiKey={apiKey}")
//    suspend fun getStories(
//        keyword: String,
//        apiKey: String
//    ): Response<NewsApiItem>
}
package com.example.composeModul5.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "fd060e949328841b812369c68ab69275",
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "fd060e949328841b812369c68ab69275",
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "fd060e949328841b812369c68ab69275",
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    companion object {
        const val CATEGORY_POPULAR = "popular"
        const val CATEGORY_TOP_RATED = "top_rated"
        const val CATEGORY_NOW_PLAYING = "now_playing"
    }
}
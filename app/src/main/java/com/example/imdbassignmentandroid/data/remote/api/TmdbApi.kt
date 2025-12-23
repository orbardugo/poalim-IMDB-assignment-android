package com.example.imdbassignmentandroid.data.remote.api

import com.example.imdbassignmentandroid.data.remote.dto.PopularMoviesResponse
import com.example.imdbassignmentandroid.data.remote.dto.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PopularTvResponse

}
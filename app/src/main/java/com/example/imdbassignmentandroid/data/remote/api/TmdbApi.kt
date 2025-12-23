package com.example.imdbassignmentandroid.data.remote.api

import com.example.imdbassignmentandroid.data.remote.dto.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse
}
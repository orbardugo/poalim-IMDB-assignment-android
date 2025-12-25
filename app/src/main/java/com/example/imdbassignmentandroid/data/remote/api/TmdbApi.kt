package com.example.imdbassignmentandroid.data.remote.api

import com.example.imdbassignmentandroid.data.remote.dto.MovieDetailsDto
import com.example.imdbassignmentandroid.data.remote.dto.PopularMoviesResponse
import com.example.imdbassignmentandroid.data.remote.dto.PopularTvResponse
import com.example.imdbassignmentandroid.data.remote.dto.TvDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
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


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailsDto

    @GET("tv/{series_id}")
    suspend fun  getTvShowDetails(
        @Path("series_id") movieId: Int,
        @Query("api_key") apiKey: String
        ): TvDetailsDto
}
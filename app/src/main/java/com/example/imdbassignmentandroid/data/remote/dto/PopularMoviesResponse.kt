package com.example.imdbassignmentandroid.data.remote.dto

import com.squareup.moshi.Json

data class PopularMoviesResponse(
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String?
)
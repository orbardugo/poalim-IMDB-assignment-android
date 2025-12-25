package com.example.imdbassignmentandroid.data.remote.dto

import com.squareup.moshi.Json

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val rating: Double,
    val runtime: Int?,
    val genres: List<GenreDto>
)
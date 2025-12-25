package com.example.imdbassignmentandroid.data.remote.dto

import com.squareup.moshi.Json

data class TvDetailsDto(
    val id: Int,
    val name: String,
    val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "vote_average") val rating: Double,
    val episode_run_time: List<Int>?,
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)
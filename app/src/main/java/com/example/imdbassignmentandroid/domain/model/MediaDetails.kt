package com.example.imdbassignmentandroid.domain.model

data class MediaDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double,
    val runtimeMinutes: Int?,
    val genres: List<String>,
    val mediaType: MediaType
)

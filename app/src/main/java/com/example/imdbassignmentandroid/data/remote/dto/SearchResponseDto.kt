package com.example.imdbassignmentandroid.data.remote.dto

import com.squareup.moshi.Json

data class SearchResponseDto(
    val results: List<SearchResultDto>
)

data class SearchResultDto(
    val id: Int,
    @Json(name = "media_type") val mediaType: String,
    val title: String?,          // movies use title
    val name: String?,           // tv uses name
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val rating: Double?
)
package com.example.imdbassignmentandroid.data.remote.dto

import com.squareup.moshi.Json

data class PopularTvResponse(
    val results: List<TvShowDto>
)

data class TvShowDto(
    val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "poster_path") val posterPath: String?
)

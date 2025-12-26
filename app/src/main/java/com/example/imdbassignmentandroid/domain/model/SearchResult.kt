package com.example.imdbassignmentandroid.domain.model

data class SearchResult(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val rating: Double,
    val mediaType: MediaType
)

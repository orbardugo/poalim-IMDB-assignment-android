package com.example.imdbassignmentandroid.ui.home

import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TvShow> = emptyList()
)


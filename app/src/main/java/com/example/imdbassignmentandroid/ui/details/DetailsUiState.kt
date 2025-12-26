package com.example.imdbassignmentandroid.ui.details

import com.example.imdbassignmentandroid.domain.model.MediaDetails

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val movie: MediaDetails? = null,
    val isFavorite: Boolean = false
)

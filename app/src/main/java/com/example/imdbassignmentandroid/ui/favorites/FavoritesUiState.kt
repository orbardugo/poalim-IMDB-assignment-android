package com.example.imdbassignmentandroid.ui.favorites

import com.example.imdbassignmentandroid.domain.model.MediaType

data class FavoriteItemUi(
    val id: Int,
    val mediaType: MediaType,
    val title: String,
    val posterPath: String?,
    val rating: Double
)

data class FavoritesUiState(
    val items: List<FavoriteItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

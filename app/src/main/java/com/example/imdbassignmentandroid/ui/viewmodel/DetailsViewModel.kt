package com.example.imdbassignmentandroid.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbassignmentandroid.data.local.FavoritesStore
import com.example.imdbassignmentandroid.data.repository.TmdbRepository
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.ui.details.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: TmdbRepository,
    private val favoritesStore: FavoritesStore,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mediaType: MediaType = MediaType.valueOf(
        checkNotNull(savedStateHandle["mediaType"])
    )
    private val itemId: Int = checkNotNull(savedStateHandle["itemId"]) {
        "itemId is required for DetailsViewModel"
    }

    private val _uiState = MutableStateFlow(DetailsUiState(isLoading = true))
    val uiState: StateFlow<DetailsUiState> = _uiState

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            val favorite = favoritesStore.isFavorite(mediaType, itemId)

            _uiState.value = DetailsUiState(
                isLoading = true,
                isFavorite = favorite
            )

            runCatching {
                when (mediaType) {
                    MediaType.MOVIE -> repository.getMovieDetails(itemId)
                    MediaType.TV    -> repository.getTvShowDetails(itemId)
                    MediaType.UNKNOWN -> error("Unsupported media type: $mediaType")
                }
            }.onSuccess { movie ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    movie = movie,
                    error = null
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load details"
                )
            }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            favoritesStore.toggleFavorite(mediaType, itemId)

            val current = _uiState.value.isFavorite
            _uiState.value = _uiState.value.copy(
                isFavorite = !current
            )
        }
    }
}

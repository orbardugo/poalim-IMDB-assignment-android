package com.example.imdbassignmentandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbassignmentandroid.data.local.FavoritesStore
import com.example.imdbassignmentandroid.data.repository.TmdbRepository
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.ui.favorites.FavoriteItemUi
import com.example.imdbassignmentandroid.ui.favorites.FavoritesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesStore: FavoritesStore,
    private val repository: TmdbRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState

    fun loadFavorites() {
        viewModelScope.launch {
            val raw = favoritesStore.getFavoritesRaw()

            if (raw.isEmpty()) {
                _uiState.value = FavoritesUiState(
                    items = emptyList(),
                    isLoading = false,
                    error = null
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            runCatching {
                raw.mapNotNull { key ->
                    parseFavoriteKey(key)?.let { (mediaType, id) ->
                        val details = when (mediaType) {
                            MediaType.MOVIE -> repository.getMovieDetails(id)
                            MediaType.TV -> repository.getTvShowDetails(id)
                            MediaType.UNKNOWN -> return@mapNotNull null
                        }

                        FavoriteItemUi(
                            id = details.id,
                            mediaType = details.mediaType,
                            title = details.title,
                            posterPath = details.posterPath,
                            rating = details.rating
                        )
                    }
                }
            }.onSuccess { items ->
                _uiState.value = FavoritesUiState(
                    items = items,
                    isLoading = false,
                    error = null
                )
            }.onFailure { e ->
                _uiState.value = FavoritesUiState(
                    items = emptyList(),
                    isLoading = false,
                    error = e.message ?: "Failed to load favorites"
                )
            }
        }
    }

    private fun parseFavoriteKey(key: String): Pair<MediaType, Int>? {
        val parts = key.split(":")
        if (parts.size != 2) return null

        val type = runCatching { MediaType.valueOf(parts[0].uppercase()) }.getOrNull()
            ?: return null
        val id = parts[1].toIntOrNull() ?: return null

        return type to id
    }
}

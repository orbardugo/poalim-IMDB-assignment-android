package com.example.imdbassignmentandroid.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            _uiState.value = DetailsUiState(isLoading = true)

            runCatching {
                when (mediaType) {
                    MediaType.MOVIE -> repository.getMovieDetails(itemId)
                    MediaType.TV    -> repository.getTvShowDetails(itemId)
                }
            }.onSuccess { movie ->
                _uiState.value = DetailsUiState(
                    isLoading = false,
                    movie = movie
                )
            }.onFailure { e ->
                _uiState.value = DetailsUiState(
                    isLoading = false,
                    error = e.message ?: "Failed to load details"
                )
            }
        }
    }
}

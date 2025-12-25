package com.example.imdbassignmentandroid.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbassignmentandroid.data.repository.TmdbRepository
import com.example.imdbassignmentandroid.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TmdbRepository
) : ViewModel() {

    // Backing property (mutable inside VM)
    private val _uiState = MutableStateFlow(HomeUiState())
    // Exposed as read-only to the UI
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                val movies = repository.getPopularMovies()
                val tvShows = repository.getPopularTvShows()
                movies to tvShows
            }.onSuccess { (movies, tvShows) ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movies = movies,
                        tvShows = tvShows,
                        error = null
                    )
                }
                Log.d("TMDB", "Movies: ${movies.size}, TvShows: ${tvShows.size}")
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
                Log.e("TMDB", "Failed to load data", e)
            }
        }
    }
}

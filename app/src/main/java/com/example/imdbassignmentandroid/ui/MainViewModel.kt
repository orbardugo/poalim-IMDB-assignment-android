package com.example.imdbassignmentandroid.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbassignmentandroid.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            runCatching { repository.getPopularMovies() }
                .onSuccess { movies ->
                    Log.d("TMDB", "Movies loaded: ${movies.size}")
                }
                .onFailure { e ->
                    Log.e("TMDB", "Failed to load movies", e)
                }
        }
    }

}
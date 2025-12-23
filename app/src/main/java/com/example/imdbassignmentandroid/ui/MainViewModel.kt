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
            runCatching {
                val movies = repository.getPopularMovies()
                val tvShows = repository.getPopularTvShows()
                movies to tvShows
            }.onSuccess { (movies, tvShows) ->
                Log.d("TMDB", "Movies loaded: ${movies.size}")
                Log.d("TMDB", "TV shows loaded: ${tvShows.size}")
            }.onFailure { e ->
                Log.e("TMDB", "Failed to load data", e)
            }
        }
    }

}
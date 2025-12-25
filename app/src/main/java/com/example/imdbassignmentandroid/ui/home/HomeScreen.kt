package com.example.imdbassignmentandroid.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow
import com.example.imdbassignmentandroid.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    onTvShowClick: (TvShow) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> Text("Loading...")
            state.error != null -> Text("Error: ${state.error}")
            else -> HomeContent(
                movies = state.movies,
                tvShows = state.tvShows,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick,
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick
            )
        }
    }
}

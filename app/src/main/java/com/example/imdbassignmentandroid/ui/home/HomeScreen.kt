package com.example.imdbassignmentandroid.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow
import com.example.imdbassignmentandroid.ui.favorites.FavoritesModal
import com.example.imdbassignmentandroid.ui.search.SearchModal
import com.example.imdbassignmentandroid.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    onTvShowClick: (TvShow) -> Unit,
    onNavigateToDetails: (MediaType, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showSearch by remember { mutableStateOf(false) }
    var showFavorites by remember { mutableStateOf(false) }


    if (showSearch) {
        SearchModal(
            onItemClick = { mediaType, id ->
                onNavigateToDetails(mediaType, id)
                showSearch = false
            },
            onClose = { showSearch = false }
        )
    }

    if (showFavorites) {
        FavoritesModal(
            onItemClick = { mediaType, id ->
                showFavorites = false
                onNavigateToDetails(mediaType, id)
            },
            onClose = { showFavorites = false }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> Text("Error: ${state.error}")
            else -> HomeContent(
                movies = state.movies,
                tvShows = state.tvShows,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick,
                onSearchClick = { showSearch = true },
                onFavoritesClick = { showFavorites = true }
            )
        }
    }
}

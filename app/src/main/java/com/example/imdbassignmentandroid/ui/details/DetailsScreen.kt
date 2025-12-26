package com.example.imdbassignmentandroid.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imdbassignmentandroid.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            state.error?.let { errorText ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorText)
                }
            }
        }
        else -> {
            state.movie?.let { details ->
                DetailsContent(
                    details = details,
                    isFavorite = state.isFavorite,
                    onFavoriteClick = viewModel::onFavoriteClick
                )
            }
        }
    }
}

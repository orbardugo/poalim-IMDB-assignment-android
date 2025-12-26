package com.example.imdbassignmentandroid.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.ui.common.MediaGridItem
import com.example.imdbassignmentandroid.ui.viewmodel.FavoritesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesModal(
    onItemClick: (MediaType, Int) -> Unit,
    onClose: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Load favorites when the modal opens
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    ModalBottomSheet(
        onDismissRequest = onClose
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    Text(
                        text = "Loading favorites...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                state.items.isEmpty() -> {
                    Text(
                        text = "No favorites yet.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                    ) {
                        items(state.items) { item ->
                            MediaGridItem(
                                title = item.title,
                                posterPath = item.posterPath,
                                rating = item.rating,
                                onClick = { onItemClick(item.mediaType, item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

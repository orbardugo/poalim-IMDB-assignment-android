package com.example.imdbassignmentandroid.ui.search

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.ui.viewmodel.SearchViewModel

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchModal(
    onItemClick: (MediaType, Int) -> Unit,
    onClose: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onClose
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Search bar at top of modal
            OutlinedTextField(
                value = state.query,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search movies and TV shows") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Text("Searching...", style = MaterialTheme.typography.bodyMedium)
            } else if (state.error != null) {
                Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grid of results: image, title, rating
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
            ) {
                items(state.results) { item ->
                    SearchResultItem(
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

@Composable
private fun SearchResultItem(
    title: String,
    posterPath: String?,
    rating: Double,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(110.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick
            )
    ) {
        AsyncImage(
            model = posterPath?.let { TMDB_IMAGE_BASE_URL + it },
            contentDescription = title,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )

        Text(
            text = "★ ${"%.1f".format(rating)}",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

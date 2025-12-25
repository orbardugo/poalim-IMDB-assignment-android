package com.example.imdbassignmentandroid.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imdbassignmentandroid.domain.model.MediaDetails
import com.example.imdbassignmentandroid.domain.model.MediaType

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

@Composable
fun DetailsContent(details: MediaDetails) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val shareUrl = when (details.mediaType) {
        MediaType.MOVIE -> "https://www.themoviedb.org/movie/${details.id}"
        MediaType.TV -> "https://www.themoviedb.org/tv/${details.id}"
    }
    val trailerQuery = Uri.encode("${details.title} trailer")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        // Display header image using backdrop if available, otherwise fallback to poster.
        AsyncImage(
            model = details.backdropPath?.let { TMDB_IMAGE_BASE_URL + it }
                ?: details.posterPath?.let { TMDB_IMAGE_BASE_URL + it },
            contentDescription = details.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // title
            Text(
                text = details.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Basic metadata row: release year, rating, and runtime
            Row(verticalAlignment = Alignment.CenterVertically) {
                details.releaseDate?.takeIf { it.isNotBlank() }?.let {
                    Text(it.take(4), style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Text(
                    text = "Rating: ${"%.1f".format(details.rating)}",
                    style = MaterialTheme.typography.bodyMedium
                )

                details.runtimeMinutes?.let {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${it} min", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // List of genres (flattened to a readable string)
            if (details.genres.isNotEmpty()) {
                Text(
                    text = details.genres.joinToString(" • "),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Movie/tv show overview
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details.overview,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Actions: open trailer search on youtube or share the movie link externally
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Opens a YouTube search page for the movie trailer.
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/results?search_query=$trailerQuery")
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Text("Watch Trailer")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Shares a link to the movie page on TMDB using the system share sheet.
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Check out ${details.title}: $shareUrl")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share"))
                    }
                ) {
                    Text("Share")
                }
            }
        }
    }
}

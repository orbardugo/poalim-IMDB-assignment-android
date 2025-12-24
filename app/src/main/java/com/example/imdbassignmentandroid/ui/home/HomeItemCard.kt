package com.example.imdbassignmentandroid.ui.home

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"


@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val indication: Indication = LocalIndication.current

    Column(
        modifier = modifier
            .width(140.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick
            )
    ) {
        AsyncImage(
            model = movie.posterPath?.let { TMDB_IMAGE_BASE_URL + it },
            contentDescription = movie.title,
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "⭐ ${"%.1f".format(movie.rating)}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TvShowCard(
    tvShow: TvShow,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val indication: Indication = LocalIndication.current

    Column(
        modifier = modifier
            .width(140.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick
            )
    ) {
        AsyncImage(
            model = tvShow.posterPath?.let { TMDB_IMAGE_BASE_URL + it },
            contentDescription = tvShow.name,
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = tvShow.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "⭐ ${"%.1f".format(tvShow.rating)}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
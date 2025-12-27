package com.example.imdbassignmentandroid.ui.common

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun MediaGridItem(
    title: String,
    posterPath: String?,
    rating: Double?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Column(
        modifier = modifier
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
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        rating?.let {
            Text(
                text = "★ ${"%.1f".format(it)}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
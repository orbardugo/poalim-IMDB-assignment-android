package com.example.imdbassignmentandroid.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow

@Composable
fun HorizontalMovieRow(
    movies: List<Movie>,
    onItemClick: (Movie) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                onClick = { onItemClick(movie) }
            )
        }
    }
}

@Composable
fun HorizontalShowRow(
    tvShows: List<TvShow>,
    onItemClick: (TvShow) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tvShows) { tv ->
            TvShowCard(
                tvShow = tv,
                onClick = { onItemClick(tv) }
            )
        }
    }
}
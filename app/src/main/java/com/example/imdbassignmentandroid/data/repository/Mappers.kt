package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.data.remote.dto.MovieDetailsDto
import com.example.imdbassignmentandroid.data.remote.dto.MovieDto
import com.example.imdbassignmentandroid.data.remote.dto.TvDetailsDto
import com.example.imdbassignmentandroid.data.remote.dto.TvShowDto
import com.example.imdbassignmentandroid.domain.model.MediaDetails
import com.example.imdbassignmentandroid.domain.model.MediaType
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        rating = rating
    )

fun TvShowDto.toDomain(): TvShow =
    TvShow(
        id = id,
        name = name,
        posterPath = posterPath,
        rating = rating
    )

fun MovieDetailsDto.toDomain() = MediaDetails(
    id = id,
    title = title,
    overview = overview.orEmpty(),
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    rating = rating,
    runtimeMinutes = runtime,
    genres = genres.map { it.name },
    mediaType = MediaType.MOVIE
)

fun TvDetailsDto.toDomain() = MediaDetails(
    id = id,
    title = name,
    overview = overview.orEmpty(),
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = firstAirDate,
    rating = rating,
    runtimeMinutes = episode_run_time?.firstOrNull(),
    genres = genres.map { it.name },
    mediaType = MediaType.TV
)
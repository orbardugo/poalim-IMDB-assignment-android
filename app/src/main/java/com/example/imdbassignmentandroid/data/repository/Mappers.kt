package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.data.remote.dto.MovieDto
import com.example.imdbassignmentandroid.data.remote.dto.TvShowDto
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        posterPath = posterPath
    )

fun TvShowDto.toDomain(): TvShow =
    TvShow(
        id = id,
        name = name,
        posterPath = posterPath
    )
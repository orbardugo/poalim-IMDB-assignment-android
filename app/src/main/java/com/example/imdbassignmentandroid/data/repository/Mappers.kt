package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.data.remote.dto.MovieDto
import com.example.imdbassignmentandroid.domain.model.Movie

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        posterPath = posterPath
    )
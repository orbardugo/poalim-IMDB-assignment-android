package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.BuildConfig
import com.example.imdbassignmentandroid.data.remote.api.TmdbApi
import com.example.imdbassignmentandroid.domain.model.Movie
import javax.inject.Inject


interface MoviesRepository {
    suspend fun getPopularMovies(): List<Movie>
}

class MoviesRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        api.getPopularMovies(BuildConfig.TMDB_API_KEY)
            .results
            .map { it.toDomain() }
}
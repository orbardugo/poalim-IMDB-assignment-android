package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.BuildConfig
import com.example.imdbassignmentandroid.data.remote.api.TmdbApi
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.TvShow
import javax.inject.Inject


interface MoviesRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getPopularTvShows(): List<TvShow>

}

class MoviesRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        api.getPopularMovies(BuildConfig.TMDB_API_KEY)
            .results
            .map { it.toDomain() }

    override suspend fun getPopularTvShows(): List<TvShow> =
        api.getPopularTvShows(BuildConfig.TMDB_API_KEY)
            .results
            .map { it.toDomain() }
}
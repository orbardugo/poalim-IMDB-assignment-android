package com.example.imdbassignmentandroid.data.repository

import com.example.imdbassignmentandroid.BuildConfig
import com.example.imdbassignmentandroid.data.remote.api.TmdbApi
import com.example.imdbassignmentandroid.domain.model.Movie
import com.example.imdbassignmentandroid.domain.model.MediaDetails
import com.example.imdbassignmentandroid.domain.model.TvShow
import javax.inject.Inject


interface TmdbRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getPopularTvShows(): List<TvShow>
    suspend fun getMovieDetails(movieId: Int): MediaDetails
    suspend fun getTvShowDetails(tvShowId: Int): MediaDetails
}

class TmdbRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : TmdbRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        api.getPopularMovies(BuildConfig.TMDB_API_KEY)
            .results
            .map { it.toDomain() }

    override suspend fun getPopularTvShows(): List<TvShow> =
        api.getPopularTvShows(BuildConfig.TMDB_API_KEY)
            .results
            .map { it.toDomain() }

    override suspend fun getMovieDetails(movieId: Int): MediaDetails =
        api.getMovieDetails(movieId, BuildConfig.TMDB_API_KEY).toDomain()

    override suspend fun getTvShowDetails(tvShowId: Int): MediaDetails =
        api.getTvShowDetails(tvShowId, BuildConfig.TMDB_API_KEY).toDomain()

}
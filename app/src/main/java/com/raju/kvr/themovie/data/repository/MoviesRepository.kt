package com.raju.kvr.themovie.data.repository

import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getGenres(): Result<Boolean>

    suspend fun getMovies(
        category: String,
        page: Int
    ): Result<Movies>

    suspend fun searchMovies(
        query: String,
        page: Int
    ): Result<Movies>

    suspend fun getMovieDetail(
        movieId: Long
    ): Result<MovieDetail>

    suspend fun addToFavourite(movieDetail: MovieDetail)

    suspend fun deleteFromFavourite(movieId: Long)

    fun getMovieFromDb(movieId: Long): Flow<MovieDetail?>

    fun getFavouriteMovies(): Flow<List<Movie>>

}
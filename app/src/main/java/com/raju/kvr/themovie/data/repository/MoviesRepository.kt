package com.raju.kvr.themovie.data.repository

import androidx.lifecycle.LiveData
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getGenres(): Flow<Boolean>

    fun getMovies(
        category: String,
        page: Int
    ): Flow<Movies>

    fun searchMovies(
        query: String,
        page: Int
    ): Flow<Movies>

    fun getMovieDetail(
        movieId: Long
    ): Flow<MovieDetail>

    suspend fun addToFavourite(movieDetail: MovieDetail)

    suspend fun deleteFromFavourite(movieId: Long)

    fun getMovieFromDb(movieId: Long): Flow<MovieDetail?>

    fun getFavouriteMovies(): Flow<List<Movie>>

}
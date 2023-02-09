package com.raju.kvr.themovie.data.repository

import androidx.lifecycle.LiveData
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies

interface MoviesRepository {

    suspend fun getGenres(): Map<Long, String>

    suspend fun getMovies(
        category: String,
        page: Int
    ): Movies

    suspend fun searchMovies(
        query: String,
        page: Int
    ): Movies

    suspend fun getMovieDetail(
        movieId: Long
    ): MovieDetail

    suspend fun addToFavourite(movieDetail: MovieDetail)

    suspend fun deleteFromFavourite(movieId: Long)

    fun getMovieLiveDataFromDb(movieId: Long): LiveData<MovieDetail?>

    suspend fun getMovieFromDb(movieId: Long): MovieDetail?

}
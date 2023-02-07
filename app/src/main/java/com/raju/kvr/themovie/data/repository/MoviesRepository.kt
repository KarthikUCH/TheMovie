package com.raju.kvr.themovie.data.repository

import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies

interface MoviesRepository {

    suspend fun getGenres(): Map<Long, String>

    suspend fun getMovies(
        category: String,
        page: Int
    ): Movies

    suspend fun getMovieDetail(
        movieId: Long
    ): MovieDetail

}
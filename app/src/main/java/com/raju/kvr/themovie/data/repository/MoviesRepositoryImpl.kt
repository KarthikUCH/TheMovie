package com.raju.kvr.themovie.data.repository

import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.remote.model.asDomainModel
import com.raju.kvr.themovie.data.remote.model.mapWithName
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(private val movieApi: MovieApi) : MoviesRepository {

    private var genreMap: Map<Long, String> = emptyMap()

    override suspend fun getGenres(): Map<Long, String> {
        return withContext(Dispatchers.IO) {
            genreMap.ifEmpty {
                genreMap = movieApi.getGenreList().genres.mapWithName()
                genreMap
            }
        }
    }

    override suspend fun getMovies(
        category: String,
        page: Int
    ): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getMovieList(category, page).asDomainModel(genreMap)
        }
    }

    override suspend fun getMovieDetail(
        movieId: Long
    ): MovieDetail {
        return withContext(Dispatchers.IO) {
            movieApi.getMovieDetail(movieId).asDomainModel()
        }
    }
}
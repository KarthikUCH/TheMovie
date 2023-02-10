package com.raju.kvr.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.raju.kvr.themovie.data.db.dao.FavouriteMovieDao
import com.raju.kvr.themovie.data.db.entity.asDomainModal
import com.raju.kvr.themovie.data.db.entity.asDomainModel
import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.remote.model.asDomainModel
import com.raju.kvr.themovie.data.remote.model.mapWithName
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies
import com.raju.kvr.themovie.domain.model.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val movieApi: MovieApi,
    private val favouriteMovieDao: FavouriteMovieDao
) : MoviesRepository {

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

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.searchMovies(query, page).asDomainModel(genreMap)
        }
    }

    override suspend fun getMovieDetail(
        movieId: Long
    ): MovieDetail {
        return withContext(Dispatchers.IO) {
            movieApi.getMovieDetail(movieId).asDomainModel()
        }
    }

    override suspend fun addToFavourite(movieDetail: MovieDetail) {
        withContext(Dispatchers.IO) {
            favouriteMovieDao.insert(movieDetail.asDbModel())
        }
    }

    override suspend fun deleteFromFavourite(movieId: Long) {
        withContext(Dispatchers.IO) {
            favouriteMovieDao.delete(movieId)
        }
    }

    override fun getMovieLiveDataFromDb(movieId: Long): LiveData<MovieDetail?> {
        return Transformations.map(favouriteMovieDao.getMovieLiveData(movieId)) {
            it?.asDomainModel()
        }
    }

    override suspend fun getMovieFromDb(movieId: Long): MovieDetail? {
        return withContext(Dispatchers.IO) {
            favouriteMovieDao.getMovie(movieId)?.asDomainModel()
        }
    }

    override fun getFavouriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(favouriteMovieDao.getMovieList()) {
            it.asDomainModal()
        }
    }
}
package com.raju.kvr.themovie.data.repository

import com.raju.kvr.themovie.data.db.dao.FavouriteMovieDao
import com.raju.kvr.themovie.data.db.entity.asDomainModal
import com.raju.kvr.themovie.data.db.entity.asDomainModel
import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.data.remote.model.asDomainModel
import com.raju.kvr.themovie.data.remote.model.mapWithName
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.Movies
import com.raju.kvr.themovie.domain.model.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val movieApi: MovieApi,
    private val favouriteMovieDao: FavouriteMovieDao
) : MoviesRepository {

    private var genreMap: Map<Long, String> = emptyMap()
    override suspend fun getGenres(): Result<Boolean> = withContext(Dispatchers.IO) {
        genreMap.ifEmpty {
            try {
                genreMap = movieApi.getGenreList().genres.mapWithName()
            } catch (e: Exception) {
                return@withContext Result.Failure(e, "Pls Try Again Later")
            }
            genreMap
        }
        Result.Success(genreMap.isNotEmpty())
    }

    override fun getMovies(
        category: String,
        page: Int
    ): Flow<Movies> = flow<Movies> {
        emit(movieApi.getMovieList(category, page).asDomainModel(genreMap))
    }.flowOn(Dispatchers.IO)

    override fun searchMovies(
        query: String,
        page: Int
    ): Flow<Movies> = flow<Movies> {
        emit(movieApi.searchMovies(query, page).asDomainModel(genreMap))
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetail(
        movieId: Long
    ): Flow<MovieDetail> = flow {
        emit(movieApi.getMovieDetail(movieId).asDomainModel())
    }.flowOn(Dispatchers.IO)

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

    override fun getMovieFromDb(movieId: Long): Flow<MovieDetail?> {
        return favouriteMovieDao.getMovie(movieId).map {
            it?.asDomainModel()
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavouriteMovies(): Flow<List<Movie>> {
        return favouriteMovieDao.getMovieList().map {
            it.asDomainModal()
        }
    }
}
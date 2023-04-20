package com.raju.kvr.themovie.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {

    @Query("SELECT * from favourite_movie")
    fun getMovieList(): Flow<List<FavouriteMovie>>

    @Query("SELECT * from favourite_movie WHERE movie_id = :movieId")
    fun getMovie(movieId: Long): Flow<FavouriteMovie?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavouriteMovie)

    @Query("DELETE from favourite_movie WHERE movie_id = :movieId")
    suspend fun delete(movieId: Long)
}
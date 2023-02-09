package com.raju.kvr.themovie.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie

@Dao
interface FavouriteMovieDao {

    @Query("SELECT * from favourite_movie")
    fun getMovieList(): LiveData<List<FavouriteMovie>>

    @Query("SELECT * from favourite_movie WHERE movie_id = :movieId")
    fun getMovieLiveData(movieId: Long): LiveData<FavouriteMovie?>

    @Query("SELECT * from favourite_movie WHERE movie_id = :movieId")
    suspend fun getMovie(movieId: Long): FavouriteMovie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavouriteMovie)

    @Query("DELETE from favourite_movie WHERE movie_id = :movieId")
    suspend fun delete(movieId: Long)
}
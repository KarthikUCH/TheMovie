package com.raju.kvr.themovie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie
import com.raju.kvr.themovie.data.db.dao.FavouriteMovieDao

@Database(entities = [FavouriteMovie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun favouriteMovieDao(): FavouriteMovieDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: MoviesDatabase

        fun getDatabase(context: Context): MoviesDatabase {
            synchronized(MoviesDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MoviesDatabase::class.java,
                        "movies_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
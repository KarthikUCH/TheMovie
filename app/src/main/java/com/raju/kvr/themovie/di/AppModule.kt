package com.raju.kvr.themovie.di

import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(movieApi: MovieApi): MoviesRepository {
        return MoviesRepositoryImpl(movieApi)
    }

}
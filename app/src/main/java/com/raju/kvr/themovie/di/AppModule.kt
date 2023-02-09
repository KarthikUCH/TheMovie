package com.raju.kvr.themovie.di

import android.content.Context
import com.raju.kvr.themovie.data.db.MoviesDatabase
import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.data.repository.MoviesRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build().create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(movieApi: MovieApi, moviesDatabase: MoviesDatabase): MoviesRepository {
        return MoviesRepositoryImpl(movieApi, moviesDatabase.favouriteMovieDao())
    }

    @Singleton
    @Provides
    fun provideMoviesDatabase(@ApplicationContext appContext: Context): MoviesDatabase{
        return MoviesDatabase.getDatabase(appContext)
    }

}
package com.raju.kvr.themovie.data.remote

import com.raju.kvr.themovie.BuildConfig
import com.raju.kvr.themovie.data.remote.model.GenreListResponse
import com.raju.kvr.themovie.data.remote.model.MovieDetailResponse
import com.raju.kvr.themovie.data.remote.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/genre/movie/list")
    suspend fun getGenreList(@Query("api_key") apiKey: String = BuildConfig.API_KEY): GenreListResponse

    @GET("3/movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailResponse
}
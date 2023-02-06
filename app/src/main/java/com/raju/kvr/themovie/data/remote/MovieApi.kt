package com.raju.kvr.themovie.data.remote

import com.raju.kvr.themovie.data.remote.model.GenreListResponse
import com.raju.kvr.themovie.data.remote.model.MovieDetailResponse
import com.raju.kvr.themovie.data.remote.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/genre/movie/list")
    suspend fun getGenreList(): GenreListResponse

    @GET("3/movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse
}
package com.raju.kvr.themovie.data.remote.model

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "poster_path") val poster: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "genre_ids") val genre: List<Long>?,
    @Json(name = "release_data") val releaseData: String?,
    @Json(name = "vote_average") val voteAverage: String?,
    @Json(name = "vote_count") val voteCount: String?,
)

data class MovieListResponse(
    @Json(name = "page") val page: Long?,
    @Json(name = "total_pages") val totalPages: Long?,
    @Json(name = "results") val movies: List<MovieResponse>?,
)

package com.raju.kvr.themovie.data.remote.model

import com.raju.kvr.themovie.domain.model.Genre
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.Movies
import com.raju.kvr.themovie.toDate
import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "poster_path") val poster: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "genre_ids") val genreIdList: List<Long> = emptyList(),
    @Json(name = "release_date") val releaseData: String?,
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "vote_count") val voteCount: Double = 0.0,
)

fun List<MovieResponse>.asDomainModel(genreMap: Map<Long, String>): List<Movie> {
    return map { it ->
        Movie(
            id = it.id,
            poster = it.poster ?: "",
            title = it.title ?: "",
            genres = it.genreIdList.map { id -> Genre(id, genreMap[id] ?: "") },
            releaseDate = it.releaseData?.toDate() ?: "",
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

data class MovieListResponse(
    @Json(name = "page") val page: Int = 0,
    @Json(name = "total_pages") val totalPages: Int = 0,
    @Json(name = "results") val movies: List<MovieResponse> = emptyList(),
)

fun MovieListResponse.asDomainModel(genreMap: Map<Long, String>): Movies {
    return Movies(
        page = page,
        totalPages = totalPages,
        movieList = movies.asDomainModel(genreMap)
    )
}

package com.raju.kvr.themovie.domain.model

import com.raju.kvr.themovie.asString
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie

data class Movies(
    val page: Int,
    val totalPages: Int,
    val movieList: List<Movie>
)

data class Movie(
    val id: Long,
    val poster: String,
    val title: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Double,
)

data class MovieDetail(
    val id: Long,
    val poster: String,
    val title: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Double,
    val backdrop: String,
    val tagline: String,
    val overview: String,
    val languages: List<Language>,
    val status: String,
) {
    companion object {
        val emptyMovieDetail = MovieDetail(
            -1, "", "", emptyList(), "", 0.0,
            0.0, "", "", "", emptyList(), ""
        )
    }
}

fun MovieDetail.asDbModel(): FavouriteMovie {
    return FavouriteMovie(
        movieId = id,
        poster = poster,
        title = title,
        genres = genres.asString(),
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        backdrop = backdrop,
        tagline = tagline,
        overview = overview,
        languages = languages.asString(),
        status = status
    )
}



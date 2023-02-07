package com.raju.kvr.themovie.domain.model

data class Movies(
    val page: Long,
    val totalPages: Long,
    val movies: List<Movie>
)

data class Movie(
    val id: Long,
    val poster: String,
    val title: String,
    val genres: List<Genre>,
    val releaseData: String,
    val voteAverage: Double,
    val voteCount: Double,
)

data class MovieDetail(
    val id: Long,
    val poster: String,
    val title: String,
    val genres: List<Genre>,
    val releaseData: String,
    val voteAverage: Double,
    val voteCount: Double,
    val backdrop: String,
    val tagline: String,
    val overview: String,
    val languages: List<Language>,
    val status: String,
)



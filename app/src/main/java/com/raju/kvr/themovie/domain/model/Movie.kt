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
    val voteAverage: Int,
    val voteCount: Int,
)

data class MovieDetail(
    val id: Long,
    val poster: String,
    val title: String,
    val genres: List<Genre>,
    val releaseData: String,
    val voteAverage: Int,
    val voteCount: Int,
    val backdrop: String,
    val tagline: String,
    val overview: String,
    val languages: List<Language>,
    val status: String,
)



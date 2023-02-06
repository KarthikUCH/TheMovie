package com.raju.kvr.themovie.data.remote.model

import com.squareup.moshi.Json

data class MovieDetailResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "poster_path") val poster: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "genres") val genres: List<GenreResponse>?,
    @Json(name = "release_data") val releaseData: String?,
    @Json(name = "vote_average") val voteAverage: String?,
    @Json(name = "vote_count") val voteCount: String?,
    @Json(name = "backdrop_path") val backdrop: String?,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "spoken_languages") val languages: List<Language>?,
    @Json(name = "status") val status: String?,
)

data class Language(
    @Json(name = "name") val name: String?,
    @Json(name = "english_name") val englishName: String?,
)

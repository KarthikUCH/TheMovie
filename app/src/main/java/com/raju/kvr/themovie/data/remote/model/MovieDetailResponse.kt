package com.raju.kvr.themovie.data.remote.model

import com.raju.kvr.themovie.domain.model.Language
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.toDate
import com.squareup.moshi.Json

data class MovieDetailResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "poster_path") val poster: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "genres") val genres: List<GenreResponse> = emptyList(),
    @Json(name = "release_data") val releaseData: String?,
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "vote_count") val voteCount: Double = 0.0,
    @Json(name = "backdrop_path") val backdrop: String?,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "spoken_languages") val languages: List<LanguageResponse> = emptyList(),
    @Json(name = "status") val status: String?,
)

data class LanguageResponse(
    @Json(name = "name") val name: String?,
    @Json(name = "english_name") val englishName: String?,
)

fun List<LanguageResponse>.asDomainModel(): List<Language> {
    return map {
        Language(
            name = it.name ?: "",
            englishName = it.englishName ?: ""
        )
    }
}

fun MovieDetailResponse.asDomainModel(): MovieDetail {
    return MovieDetail(
        id = id,
        poster = poster ?: "",
        title = title ?: "",
        genres = genres.asDomainModel(),
        releaseData = releaseData?.toDate() ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        backdrop = backdrop ?: "",
        tagline = tagline ?: "",
        overview = overview ?: "",
        languages = languages.asDomainModel(),
        status = status ?: ""
    )
}

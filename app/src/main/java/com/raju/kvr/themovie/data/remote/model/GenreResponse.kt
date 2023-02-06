package com.raju.kvr.themovie.data.remote.model

import com.raju.kvr.themovie.domain.model.Genre
import com.squareup.moshi.Json

data class GenreResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
)

data class GenreListResponse(
    @Json(name = "genres") val genres: List<GenreResponse> = emptyList(),
)

fun List<GenreResponse>.mapWithName(): Map<Long, String> {
    return associate {
        it.id to it.name!!
    }
}

fun List<GenreResponse>.asDomainModel(): List<Genre> {
    return map {
        Genre(
            id = it.id,
            name = it.name ?: ""
        )
    }
}


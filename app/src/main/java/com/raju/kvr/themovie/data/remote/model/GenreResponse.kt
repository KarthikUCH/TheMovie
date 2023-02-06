package com.raju.kvr.themovie.data.remote.model

import com.squareup.moshi.Json

data class GenreResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "name") val name: String?,
)

data class GenreListResponse(
    @Json(name = "genres") val genres: List<GenreResponse>?,
)

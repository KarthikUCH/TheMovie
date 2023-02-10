package com.raju.kvr.themovie.domain.model

data class Genre(
    val id: Long,
    val name: String,
)

fun List<Genre>.toUiData(): String {
    val separator = ", "

    val sb = StringBuilder()
    map {
        sb.append(it.name).append(separator)
    }
    return sb.removeSuffix(separator).toString()
}

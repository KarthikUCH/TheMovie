package com.raju.kvr.themovie.domain.model

data class Language(
    val name: String,
    val englishName: String,
)

fun List<Language>.toUiData(): String {
    val separator = ", "

    val sb = StringBuilder()
    map {
        sb.append(it.englishName).append(separator)
    }
    return sb.removeSuffix(separator).toString()
}

package com.raju.kvr.themovie

import java.text.SimpleDateFormat

fun String.toDate(): String {
    return try {
        val formatter = SimpleDateFormat("dd/mmm/yyyy")
        formatter.format(this)
    } catch (e: Exception) {
        ""
    }
}
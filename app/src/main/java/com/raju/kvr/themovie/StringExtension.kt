package com.raju.kvr.themovie

import java.text.SimpleDateFormat

fun String.toDate(): String {
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(this)
        val formatter = SimpleDateFormat("dd/MMM/yyyy")
        formatter.format(date)
    } catch (e: Exception) {
        ""
    }
}
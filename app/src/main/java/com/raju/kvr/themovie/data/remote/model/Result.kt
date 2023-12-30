package com.raju.kvr.themovie.data.remote.model

sealed interface Result<T> {
    data class Success<T>(val response: T) : Result<T>

    data class Failure<T>(val throwable: Throwable, val message: String) :
        Result<T>
}


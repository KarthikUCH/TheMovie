package com.raju.kvr.themovie

import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type


inline fun <reified T> List<T>.asString(): String {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val type: Type = Types.newParameterizedType(
        MutableList::class.java,
        T::class.java
    )
    val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return jsonAdapter.toJson(this)
}

inline fun <reified T> String.asList(): List<T> {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val type: Type = Types.newParameterizedType(
        MutableList::class.java,
        T::class.java
    )
    val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return jsonAdapter.fromJson(this).orEmpty()
}


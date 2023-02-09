package com.raju.kvr.themovie.data.remote.model

import com.raju.kvr.themovie.domain.model.Genre
import org.junit.Assert
import org.junit.Test


internal class GenreResponseTest {

    private val genreListResponse: GenreListResponse = GenreListResponse(
        listOf(
            GenreResponse(1, "Action"),
            GenreResponse(2, "Adventure"),
            GenreResponse(3, "Animation"),
            GenreResponse(4, ""),
            GenreResponse(5, null)
        )
    )

    @Test
    fun genreResponse_mapWithName() {
        val map: Map<Long, String> = genreListResponse.genres.mapWithName()

        Assert.assertEquals(5, map.size)
        Assert.assertEquals("Action", map[1])
        Assert.assertEquals("Adventure", map[2])
        Assert.assertEquals("Animation", map[3])
        Assert.assertEquals("", map[4])
        Assert.assertEquals("", map[5])
    }

    @Test
    fun genreResponse_asDomainModel() {
        val map: Map<Long, String> = genreListResponse.genres.mapWithName()
        val list: List<Genre> = genreListResponse.genres.asDomainModel()
        Assert.assertEquals(5, list.size)
        for (i in 0..4) {
            Assert.assertEquals(map[list[i].id], list[i].name)
        }
    }
}
package com.raju.kvr.themovie.data.remote.model

import org.junit.Assert
import org.junit.Test


internal class MovieResponseTest {

    private val genreMap = mapOf(
        1L to "Action",
        2L to "Adventure",
        3L to "Animation"
    )

    private val movieResponse = MovieResponse(
        id = 1081893,
        poster = "poster_path",
        title = "title",
        genreIdList = listOf(1, 2, 3),
        releaseData = "2022-11-28",
        voteAverage = 0.0,
        voteCount = 0.0,
    )

    @Test
    fun movieListResponse_asDomainModel() {
        val list: List<MovieResponse> = listOf(movieResponse, movieResponse.copy(id = 1081894))
        val movieListResponse = MovieListResponse(1, 20, list)
        val result = movieListResponse.asDomainModel(genreMap)

        Assert.assertEquals(result.page, movieListResponse.page)
        Assert.assertEquals(result.totalPages, movieListResponse.totalPages)
        Assert.assertEquals(result.movieList.size, movieListResponse.movies.size)
    }

    @Test
    fun movieResponse_asDomainModel() {
        val list: List<MovieResponse> = listOf(movieResponse)
        val result = list.asDomainModel(genreMap)[0]
        Assert.assertEquals(result.id, movieResponse.id)
        Assert.assertEquals(result.poster, movieResponse.poster)
        Assert.assertEquals(result.title, movieResponse.title)
        Assert.assertEquals(result.releaseDate, "28/Nov/2022")
        Assert.assertEquals(result.voteAverage, movieResponse.voteAverage, 0.0)
        Assert.assertEquals(result.voteCount, movieResponse.voteCount, 0.0)

        for (i in 0..2) {
            Assert.assertEquals(result.genres[i].name, genreMap[result.genres[i].id])
        }
    }
}
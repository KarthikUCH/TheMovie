package com.raju.kvr.themovie.data.db.entity

import org.junit.Assert
import org.junit.Test

internal class FavouriteMovieTest {

    private val favouriteMovie = FavouriteMovie(
        id = 1,
        movieId = 1081893,
        poster = "poster_path",
        title = "title",
        genres = "[{\"id\": 1,\"name\": \"Action\"},{\"id\": 3,\"name\": \"Animation\"}]",
        releaseDate = "release_date",
        voteAverage = 0.0,
        voteCount = 0.0,
        backdrop = "backdrop_path",
        tagline = "tagline",
        overview = "overview",
        languages = "[{\"englishName\": \"English\",\"name\": \"English\"},{\"englishName\": \"German\",\"name\": \"Deutsch\"}]",
        status = "status"
    )

    @Test
    fun favouriteMovie_asDomainModel_returnMovieDetail() {
        val result = favouriteMovie.asDomainModel()

        Assert.assertEquals(favouriteMovie.movieId, result.id)
        Assert.assertEquals(favouriteMovie.poster, result.poster)
        Assert.assertEquals(favouriteMovie.title, result.title)
        Assert.assertEquals(2, result.genres.size)
        Assert.assertEquals(favouriteMovie.releaseDate, result.releaseDate)
        Assert.assertEquals(favouriteMovie.voteAverage, result.voteAverage, 0.0)
        Assert.assertEquals(favouriteMovie.voteCount, result.voteCount, 0.0)
        Assert.assertEquals(favouriteMovie.backdrop, result.backdrop)
        Assert.assertEquals(favouriteMovie.tagline, result.tagline)
        Assert.assertEquals(favouriteMovie.overview, result.overview)
        Assert.assertEquals(2, result.languages.size)
        Assert.assertEquals(favouriteMovie.status, result.status)
    }

    @Test
    fun favouriteMovie_asDomainModel_withEmptyGenreAndLanguage() {
        val result = favouriteMovie.copy(genres = "", languages = "").asDomainModel()

        Assert.assertEquals(0, result.genres.size)
        Assert.assertEquals(0, result.languages.size)
    }

    @Test
    fun favouriteMovieList_asDomainModel() {
        val list = listOf(
            favouriteMovie,
            favouriteMovie.copy(id = 1081894, genres = ""),
            favouriteMovie.copy(id = 1081895)
        )
        val result = list.asDomainModal()

        Assert.assertEquals(list.size, result.size)

        val movie1 = result[0]
        Assert.assertEquals(favouriteMovie.movieId, movie1.id)
        Assert.assertEquals(favouriteMovie.poster, movie1.poster)
        Assert.assertEquals(favouriteMovie.title, movie1.title)
        Assert.assertEquals(2, movie1.genres.size)
        Assert.assertEquals(favouriteMovie.releaseDate, movie1.releaseDate)

        val movie2 = result[1]
        Assert.assertEquals(0, movie2.genres.size)

    }
}
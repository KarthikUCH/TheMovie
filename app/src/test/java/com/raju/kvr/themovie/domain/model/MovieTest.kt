package com.raju.kvr.themovie.domain.model

import org.junit.Assert
import org.junit.Test


internal class MovieTest {

    private val genreList: List<Genre> = listOf(
        Genre(1, "Action"),
        Genre(3, "Animation")
    )

    private val languageList: List<Language> = listOf(
        Language("English", "English"),
        Language("Deutsch", "German")
    )

    private val movieDetail = MovieDetail(
        id = 1081893,
        poster = "poster_path",
        title = "title",
        genres = genreList,
        releaseDate = "release_date",
        voteAverage = 0.0,
        voteCount = 0.0,
        backdrop = "backdrop_path",
        tagline = "tagline",
        overview = "overview",
        languages = languageList,
        status = "status"
    )

    @Test
    fun movieDetail_asDbModel() {
        val favouriteMovie = movieDetail.asDbModel()
        Assert.assertEquals(movieDetail.id, favouriteMovie.movieId)
        Assert.assertEquals(movieDetail.poster, favouriteMovie.poster)
        Assert.assertEquals(movieDetail.title, favouriteMovie.title)
        Assert.assertEquals(
            "[{\"id\":1,\"name\":\"Action\"},{\"id\":3,\"name\":\"Animation\"}]",
            favouriteMovie.genres
        )
        Assert.assertEquals(movieDetail.releaseDate, favouriteMovie.releaseDate)
        Assert.assertEquals(movieDetail.voteAverage, favouriteMovie.voteAverage, 0.0)
        Assert.assertEquals(movieDetail.voteCount, favouriteMovie.voteCount, 0.0)
        Assert.assertEquals(movieDetail.backdrop, favouriteMovie.backdrop)
        Assert.assertEquals(movieDetail.tagline, favouriteMovie.tagline)
        Assert.assertEquals(movieDetail.overview, favouriteMovie.overview)
        Assert.assertEquals(
            "[{\"name\":\"English\",\"englishName\":\"English\"},{\"name\":\"Deutsch\",\"englishName\":\"German\"}]",
            favouriteMovie.languages
        )
        Assert.assertEquals(movieDetail.status, favouriteMovie.status)
    }
}
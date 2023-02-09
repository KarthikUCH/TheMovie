package com.raju.kvr.themovie.data.remote.model

import com.raju.kvr.themovie.domain.model.Language
import com.raju.kvr.themovie.domain.model.MovieDetail
import org.junit.Assert
import org.junit.Test

internal class MovieDetailResponseTest {

    private val genreList: List<GenreResponse> = listOf(
        GenreResponse(1, "Action"),
        GenreResponse(2, "Adventure"),
        GenreResponse(3, "Animation")
    )

    private val languageList: List<LanguageResponse> = listOf(
        LanguageResponse("English", "English"),
        LanguageResponse("Deutsch", "German"),
    )

    @Test
    fun languageResponse_asDomainModel() {
        val list: List<Language> = languageList.asDomainModel()
        Assert.assertEquals(languageList[0].name, list[0].name)
        Assert.assertEquals(languageList[0].englishName, list[0].englishName)

        Assert.assertEquals(languageList[1].name, list[1].name)
        Assert.assertEquals(languageList[1].englishName, list[1].englishName)
    }

    @Test
    fun languageResponse_asDomainModel_withNullData() {
        val list: List<Language> = listOf(
            LanguageResponse(null, null)
        ).asDomainModel()

        Assert.assertEquals("", list[0].name)
        Assert.assertEquals("", list[0].englishName)
    }

    @Test
    fun movieDetailResponse_asDomainModel() {

        val movieDetailResponse = MovieDetailResponse(
            id = 1081893,
            poster = "poster_path",
            title = "title",
            genres = genreList,
            releaseData = "2022-10-28",
            voteAverage = 0.0,
            voteCount = 0.0,
            backdrop = "backdrop_path",
            tagline = "tagline",
            overview = "overview",
            languages = languageList,
            status = "status"
        )
        val movieDetail: MovieDetail = movieDetailResponse.asDomainModel()

        Assert.assertEquals(movieDetailResponse.id, movieDetail.id)
        Assert.assertEquals(movieDetailResponse.poster, movieDetail.poster)
        Assert.assertEquals(movieDetailResponse.title, movieDetail.title)
        Assert.assertEquals(movieDetailResponse.genres.size, movieDetail.genres.size)
        Assert.assertEquals("28/Oct/2022", movieDetail.releaseDate)
        Assert.assertEquals(movieDetailResponse.voteAverage, movieDetail.voteAverage, 0.0)
        Assert.assertEquals(movieDetailResponse.voteCount, movieDetail.voteCount, 0.0)
        Assert.assertEquals(movieDetailResponse.backdrop, movieDetail.backdrop)
        Assert.assertEquals(movieDetailResponse.tagline, movieDetail.tagline)
        Assert.assertEquals(movieDetailResponse.overview, movieDetail.overview)
        Assert.assertEquals(movieDetailResponse.languages.size, movieDetail.languages.size)
        Assert.assertEquals(movieDetailResponse.status, movieDetail.status)
    }

    @Test
    fun movieDetailResponse_asDomainModel_withNullData() {
        val movieDetailResponse = MovieDetailResponse(
            id = 1081893,
            poster = null,
            title = null,
            genres = emptyList(),
            releaseData = null,
            voteAverage = 0.0,
            voteCount = 0.0,
            backdrop = null,
            tagline = null,
            overview = null,
            languages = emptyList(),
            status = null
        )

        val movieDetail: MovieDetail = movieDetailResponse.asDomainModel()

        Assert.assertEquals(movieDetailResponse.id, movieDetail.id)
        Assert.assertEquals("", movieDetail.poster)
        Assert.assertEquals("", movieDetail.title)
        Assert.assertEquals(movieDetailResponse.genres.size, movieDetail.genres.size)
        Assert.assertEquals("", movieDetail.releaseDate)
        Assert.assertEquals(movieDetailResponse.voteAverage, movieDetail.voteAverage, 0.0)
        Assert.assertEquals(movieDetailResponse.voteCount, movieDetail.voteCount, 0.0)
        Assert.assertEquals("", movieDetail.backdrop)
        Assert.assertEquals("", movieDetail.tagline)
        Assert.assertEquals("", movieDetail.overview)
        Assert.assertEquals(movieDetailResponse.languages.size, movieDetail.languages.size)
        Assert.assertEquals("", movieDetail.status)
    }
}
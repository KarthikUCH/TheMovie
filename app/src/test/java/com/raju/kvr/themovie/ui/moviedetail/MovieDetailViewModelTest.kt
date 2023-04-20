package com.raju.kvr.themovie.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.raju.kvr.themovie.MainDispatcherRule
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Genre
import com.raju.kvr.themovie.domain.model.Language
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.getOrAwaitValue
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
internal class MovieDetailViewModelTest {

    lateinit var viewModel: MovieDetailViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

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

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(moviesRepository)
    }

    @Test
    fun loadMovie_fromRemote() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { null }
        whenever(moviesRepository.getMovieDetail(1081893)).then { movieDetail }

        viewModel.loadMovie(1081893)
        val result = viewModel.movieDetail.getOrAwaitValue()

        verify(moviesRepository, times(1)).getMovieFromDb(1081893)
        verify(moviesRepository, times(1)).getMovieDetail(1081893)

        Assert.assertEquals(1081893, result.id)
    }


    @Test
    fun loadMovie_fromDb() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { movieDetail }

        viewModel.loadMovie(1081893)
        val result = viewModel.movieDetail.getOrAwaitValue()

        verify(moviesRepository, times(1)).getMovieFromDb(1081893)
        verify(moviesRepository, times(0)).getMovieDetail(1081893)

        Assert.assertEquals(1081893, result.id)
    }

    @Test
    fun markAsFavourite_asTrue() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { movieDetail }
        whenever(moviesRepository.addToFavourite(movieDetail)).then {  }

        viewModel.loadMovie(1081893)
        viewModel.markAsFavourite(true)
        verify(moviesRepository, times(1)).addToFavourite(movieDetail)
    }

    @Test
    fun markAsFavourite_asFalse() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { movieDetail }
        whenever(moviesRepository.deleteFromFavourite(1081893)).then {  }

        viewModel.loadMovie(1081893)
        viewModel.markAsFavourite(false)
        verify(moviesRepository, times(1)).deleteFromFavourite(1081893)
    }

    @Test
    fun isFavouriteMovie_returnTrue() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { movieDetail }
        /*whenever(moviesRepository.getMovieLiveDataFromDb(1081893)).then { MutableLiveData(movieDetail)}*/

        viewModel.loadMovie(1081893)
        Assert.assertTrue(viewModel.isFavouriteMovie(1081893).getOrAwaitValue())
    }

    @Test
    fun isFavouriteMovie_returnFalse() = runTest {
        whenever(moviesRepository.getMovieFromDb(1081893)).then { movieDetail }
        /*whenever(moviesRepository.getMovieLiveDataFromDb(1081893)).then { MutableLiveData(null)}*/

        viewModel.loadMovie(1081893)
        Assert.assertFalse(viewModel.isFavouriteMovie(1081893).getOrAwaitValue())
    }

}
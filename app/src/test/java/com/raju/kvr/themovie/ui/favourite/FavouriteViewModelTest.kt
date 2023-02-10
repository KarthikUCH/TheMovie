package com.raju.kvr.themovie.ui.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie
import com.raju.kvr.themovie.data.db.entity.asDomainModal
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.getOrAwaitValue
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
internal class FavouriteViewModelTest {

    lateinit var viewModel: FavouriteViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

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

    private val favouriteMovieList: LiveData<List<Movie>> =
        MutableLiveData(
            listOf(
                favouriteMovie,
                favouriteMovie.copy(id = 2, movieId = 1081894),
                favouriteMovie.copy(id = 3, movieId = 1081895)
            ).asDomainModal()
        )

    @Before
    fun setUp() {
        viewModel = FavouriteViewModel(moviesRepository)
    }

    @Test
    fun getMovies() {
        whenever(moviesRepository.getFavouriteMovies()).then { favouriteMovieList }

        val result = viewModel.getMovies().getOrAwaitValue()

        verify(moviesRepository, times(1)).getFavouriteMovies()

        Assert.assertEquals(3, result.size)
    }
}
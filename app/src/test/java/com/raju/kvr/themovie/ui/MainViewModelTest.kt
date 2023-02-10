package com.raju.kvr.themovie.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raju.kvr.themovie.MainDispatcherRule
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.coroutines.cancellation.CancellationException


@RunWith(MockitoJUnitRunner::class)
internal class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private val genreMap = mapOf(
        1L to "Action",
        2L to "Adventure",
        3L to "Animation"
    )

    @Test
    fun loadGenres_success() = runTest {
        whenever(moviesRepository.getGenres()).then { genreMap }
        val viewModel = MainViewModel(moviesRepository)

        Assert.assertEquals(MainViewModel.Status.SUCCESS, viewModel.status.getOrAwaitValue())
    }

    @Test
    fun loadGenres_success_withEmptyGenreList() = runTest {
        whenever(moviesRepository.getGenres()).then { emptyMap<Long, String>() }
        val viewModel = MainViewModel(moviesRepository)

        Assert.assertEquals(MainViewModel.Status.ERROR, viewModel.status.getOrAwaitValue())
    }

    @Test
    fun loadGenres_Error() = runTest {
        whenever(moviesRepository.getGenres()).thenThrow(CancellationException())
        val viewModel = MainViewModel(moviesRepository)

        Assert.assertEquals(MainViewModel.Status.ERROR, viewModel.status.getOrAwaitValue())
    }
}
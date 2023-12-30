package com.raju.kvr.themovie.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raju.kvr.themovie.MainDispatcherRule
import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.lang.RuntimeException


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
        // Given
        whenever(moviesRepository.getGenres()).then { Result.Success(true) }

        // When
        val viewModel = MainViewModel(moviesRepository)

        // Then
        Assert.assertEquals(MainViewModel.Status.SUCCESS, viewModel.status.value)
    }

    @Test
    fun loadGenres_success_withEmptyGenreList() = runTest {
        // Given
        whenever(moviesRepository.getGenres()).then { Result.Success(false) }

        // When
        val viewModel = MainViewModel(moviesRepository)

        // Then
        Assert.assertEquals(MainViewModel.Status.ERROR, viewModel.status.value)
    }
}
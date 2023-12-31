package com.raju.kvr.themovie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _status = MutableStateFlow<Status>(Status.LOADING)
    val status: StateFlow<Status> = _status

    private lateinit var category: String
    private var isSearch: Boolean = false
    private var page: Int = 1
    private var reachedLastPage = false

    fun loadMovies(category: String, page: Int, isSearch: Boolean) {
        this.category = category
        this.page = page
        this.isSearch = isSearch
        loadMovies()
    }

    fun loadMoreMovies() {
        if (reachedLastPage) {
            _status.value = Status.SUCCESS
            return
        }

        page++
        loadMovies()
    }

    private fun loadMovies() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            val moviesResult = if (isSearch) {
                moviesRepository.searchMovies(category, page)
            } else {
                moviesRepository.getMovies(category, page)
            }

            when (moviesResult) {
                is Result.Error -> {
                    _status.value = Status.ERROR
                    page--
                }

                is Result.Success -> {
                    reachedLastPage = page >= moviesResult.response.totalPages
                    _movies.value = _movies.value.plus(moviesResult.response.movieList)
                    _status.value = Status.SUCCESS
                }
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}
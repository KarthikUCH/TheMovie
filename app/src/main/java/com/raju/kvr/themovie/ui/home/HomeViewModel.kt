package com.raju.kvr.themovie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.ui.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _status = SingleLiveEvent<Status>()
    val status: LiveData<Status> = _status

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
            if (isSearch) {
                moviesRepository.searchMovies(category, page)
            } else {
                moviesRepository.getMovies(category, page)
            }.catch {
                _status.value = Status.ERROR
                page--
            }.collect { movies ->
                reachedLastPage = page >= movies.totalPages
                _movies.value = _movies.value?.plus(movies.movieList) ?: movies.movieList
                _status.value = Status.SUCCESS
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}
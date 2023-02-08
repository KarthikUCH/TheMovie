package com.raju.kvr.themovie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private lateinit var category: String
    private var isSearch: Boolean = false
    private var page: Int = 1

    fun loadMovies(category: String, page: Int, isSearch: Boolean) {
        this.category = category
        this.page = page
        this.isSearch = isSearch
        loadMovies()
    }

    fun loadMoreMovies() {
        page++
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val movies = if(isSearch){
                    moviesRepository.searchMovies(category, page)
                } else {
                    moviesRepository.getMovies(category, page)
                }
                _movies.value = _movies.value?.plus(movies.movies) ?: movies.movies
            } catch (e: Exception) {
                page--
            }
        }
    }
}
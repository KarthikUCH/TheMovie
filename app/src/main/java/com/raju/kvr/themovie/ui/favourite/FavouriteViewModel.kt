package com.raju.kvr.themovie.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {
    fun getMovies(): LiveData<List<Movie>> = moviesRepository.getFavouriteMovies().asLiveData()
}
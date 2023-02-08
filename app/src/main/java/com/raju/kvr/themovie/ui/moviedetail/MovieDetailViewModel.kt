package com.raju.kvr.themovie.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> = _movieDetail

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite = _isFavourite

    fun loadMovie(movieId: Long, isFavourite: Boolean) {
        _isFavourite.value = isFavourite
        if (isFavourite) {
            loadMovieFromLocal(movieId)
        } else {
            loadMovieFromRemote(movieId)
        }
    }

    private fun loadMovieFromRemote(movieId: Long) {
        viewModelScope.launch {
            try {
                _movieDetail.value = moviesRepository.getMovieDetail(movieId)
            } catch (e: Exception) {

            }
        }
    }

    private fun loadMovieFromLocal(movieId: Long) {

    }

    private fun markFavourite(){

    }

    private fun unMarkFavourite(){

    }

}
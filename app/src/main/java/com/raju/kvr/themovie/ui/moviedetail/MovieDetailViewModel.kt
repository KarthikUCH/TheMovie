package com.raju.kvr.themovie.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetail>(MovieDetail.emptyMovieDetail)
    val movieDetail: StateFlow<MovieDetail> = _movieDetail

    fun isFavouriteMovie(movieId: Long): StateFlow<Boolean> =
        moviesRepository.getMovieFromDb(movieId).map {
            it != null
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    fun markAsFavourite(isFavourite: Boolean) {
        viewModelScope.launch {
            movieDetail.value?.let {
                if (isFavourite) {
                    moviesRepository.addToFavourite(it)
                } else {
                    moviesRepository.deleteFromFavourite(it.id)
                }
            }
        }
    }

    fun loadMovie(movieId: Long) {
        viewModelScope.launch {

            moviesRepository.getMovieFromDb(movieId).flatMapConcat {
                it?.let {
                    flowOf(it)
                } ?: moviesRepository.getMovieDetail(movieId)
            }.catch {
                it.printStackTrace()
            }.collect {
                _movieDetail.value = it
            }
        }
    }

}
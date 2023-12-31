package com.raju.kvr.themovie.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.data.repository.MoviesRepository
import com.raju.kvr.themovie.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

            /**
             * Note: Since we are getting the Movie Detail from Room DB as flow, any change to
             * movie detail data will trigger this Intermediaries map operator.
             */
            moviesRepository.getMovieFromDb(movieId).map {
                it?.let {
                    Result.Success(it)
                } ?: moviesRepository.getMovieDetail(movieId)
            }.catch {
                it.printStackTrace()
            }.collect {
                when (it) {
                    is Result.Error -> {
                        // Do Nothing
                    }

                    is Result.Success -> {
                        _movieDetail.value = it.response
                    }
                }

            }
        }
    }

}
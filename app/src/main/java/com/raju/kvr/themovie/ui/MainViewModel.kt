package com.raju.kvr.themovie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _status = MutableStateFlow<Status>(Status.LOADING)
    val status: StateFlow<Status> = _status

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _status.value = Status.LOADING
            moviesRepository.getGenres().catch {
                _status.value = Status.ERROR
            }.collect { isSuccess ->
                _status.value = if (isSuccess) {
                    Status.SUCCESS
                } else {
                    Status.ERROR
                }
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}


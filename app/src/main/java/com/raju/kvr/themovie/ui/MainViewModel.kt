package com.raju.kvr.themovie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _status.value = Status.LOADING
            try {
                val result = moviesRepository.getGenres()
                _status.value = if (result.isNotEmpty()) {
                    Status.SUCCESS
                } else {
                    Status.ERROR
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = Status.ERROR
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}


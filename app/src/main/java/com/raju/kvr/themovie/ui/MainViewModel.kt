package com.raju.kvr.themovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.remote.model.Result
import com.raju.kvr.themovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _statusChannel = MutableStateFlow<Status?>(null)
    val status: StateFlow<Status?> = _statusChannel.asStateFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _statusChannel.value = Status.LOADING
            moviesRepository.getGenres().let { result ->
                when (result) {

                    is Result.Success -> {
                        _statusChannel.value = if (result.response) {
                            Status.SUCCESS
                        } else {
                            Status.ERROR
                        }
                    }

                    is Result.Failure -> {
                        _statusChannel.value = Status.ERROR
                    }
                }
            }
        }
    }

    fun displayedHomePage() {
        _statusChannel.value = null
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}


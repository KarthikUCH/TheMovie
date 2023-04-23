package com.raju.kvr.themovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.kvr.themovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _statusChannel =
        Channel<Status>() // Using channel inorder to get the behaviour of Single Live Event
    val status = _statusChannel.receiveAsFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _statusChannel.send(Status.LOADING)
            moviesRepository.getGenres().catch {
                _statusChannel.send(Status.ERROR)
            }.collect { isSuccess ->
                _statusChannel.send(
                    if (isSuccess) {
                        Status.SUCCESS
                    } else {
                        Status.ERROR
                    }
                )
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}


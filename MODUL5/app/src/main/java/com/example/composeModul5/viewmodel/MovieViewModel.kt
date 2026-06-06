package com.example.composeModul5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeModul5.data.local.MovieDatabase
import com.example.composeModul5.data.remote.ApiService
import com.example.composeModul5.data.repository.MovieRepository
import com.example.composeModul5.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(
        MovieDatabase.getDatabase(application),
        application.applicationContext
    )

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val currentCategory = ApiService.CATEGORY_TOP_RATED

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            repository.refreshMovies(currentCategory)
            repository.getMovies(currentCategory).collect { movies ->
                if (movies.isEmpty()) {
                    _uiState.value = MovieUiState.Error("No movies found")
                } else {
                    _uiState.value = MovieUiState.Success(movies)
                }
            }
        }
    }

    fun reloadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            repository.forceRefreshMovies(currentCategory)
            repository.getMovies(currentCategory).collect { movies ->
                if (movies.isEmpty()) {
                    _uiState.value = MovieUiState.Error("No movies found")
                } else {
                    _uiState.value = MovieUiState.Success(movies)
                }
            }
        }
    }
}

class MovieViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
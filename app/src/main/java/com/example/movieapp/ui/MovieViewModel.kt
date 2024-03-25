package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies

    private val _lastViewedMovie: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val lastViewedMovie: StateFlow<List<Movie>> get() = _lastViewedMovie

    init {
        fetchMovies()
        fetchLastViewedMovie()
    }

    private fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = movieRepository.fetchMovies()
                _movies.emit(movies)
            } catch (e: Exception) {
                // Handle error case
            }
        }
    }

    private fun fetchLastViewedMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val lastViewedMovie = movieRepository.getLastViewedMovieInfo()
            _lastViewedMovie.emit(listOf(lastViewedMovie))
        }
    }

    fun saveLastViewedMovieInfo(movie: Movie) {
        movieRepository.saveLastViewedMovieInfo(movie)
    }

}
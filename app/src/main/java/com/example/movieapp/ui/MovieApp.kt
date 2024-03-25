package com.example.movieapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.Movie

@Composable
fun MovieApp(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    viewModel: MovieViewModel
) {
    val movies by viewModel.movies.collectAsState()
    val selectedMovie = remember { mutableStateOf<Movie?>(null) }
    val isLoading = remember { mutableStateOf(false) }
    val isLastViewed = remember { mutableStateOf(false) }
    val lastViewedMovieData = remember {
        mutableStateOf(
            viewModel.getLastViewedMovieInfo()
        )
    }

    Column(verticalArrangement = Arrangement.Center) {

        FilterBar(filterText = searchText, onFilterTextChange = onSearchTextChanged)
        if (isLastViewed.value && selectedMovie.value == null) {
            Text(text = "Last Viewed")
        }
        if (selectedMovie.value != null) {
            MovieDetails(selectedMovie.value!!) { selectedMovie.value = null }
        } else if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(80.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        } else {
            val filteredMovies = movies.filter {
                it.title?.contains(searchText, ignoreCase = true) == true
            }
            MovieList(filteredMovies) { movie ->
                selectedMovie.value = movie
                viewModel.saveLastViewedMovieInfo(movie)
            }
        }
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        if (movies.isEmpty() && lastViewedMovieData.value != null) {
            viewModel.saveLastViewedMovieInfo(lastViewedMovieData.value!!)
            isLastViewed.value = true
        }

        isLoading.value = false
    }

    LaunchedEffect(selectedMovie.value) {
        if (selectedMovie.value != null) {
            viewModel.saveLastViewedMovieInfo(selectedMovie.value!!)
        }
    }
}
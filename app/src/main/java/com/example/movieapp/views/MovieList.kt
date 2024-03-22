package com.example.movieapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movieapp.api.Movie

@Composable
fun MovieList(movies: List<Movie>, onItemClick: (Movie) -> Unit) {

    if (movies.isEmpty()) {
        // Show a message indicating no movies found (optional)
        Text("No Movies Found")
    } else {
        LazyColumn {
            items(movies, key = { it.id }) { movie ->
                MovieListItem(movie) { onItemClick(movie) }
            }
        }
    }
}

package com.example.movieapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.Constants.API_KEY
import com.example.movieapp.api.Movie
import com.example.movieapp.api.TmdbApiService
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.views.FilterBar
import com.example.movieapp.views.MovieDetails
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("MovieApp", Context.MODE_PRIVATE)

        setContent {
            MovieAppTheme {
                MovieApp(sharedPreferences)
            }
        }
    }
}

@Composable
fun MovieApp(sharedPreferences: SharedPreferences) {
    val movies = remember { mutableStateListOf<Movie>() }
    val selectedMovie = remember { mutableStateOf<Movie?>(null) }
    val searchText = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val isLastViewed = remember { mutableStateOf(false) }
    val lastViewedMovieData = remember {
        mutableStateOf(
            getLastViewedMovieInfo(sharedPreferences)
        )
    }

    Column(verticalArrangement = Arrangement.Center) {
        FilterBar(searchText.value) { filterText -> searchText.value = filterText }
        if(isLastViewed.value && selectedMovie.value == null){
            Text(text = "Last Viewed")
        }
        if (selectedMovie.value != null) {
            MovieDetails(selectedMovie.value!!) { selectedMovie.value = null }
        } else if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(80.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
        } else {
            val filteredMovies = movies.filter {
                it.title?.contains(searchText.value, ignoreCase = true) == true
            }
            MovieList(filteredMovies) { movie ->
                selectedMovie.value = movie
                saveLastViewedMovieInfo(sharedPreferences, movie)
            }
        }
    }

    // Fetch movies directly in a LaunchedEffect with Dispatchers.IO
    LaunchedEffect(Unit) {
        isLoading.value = true
        val fetchedMovies = fetchMovies()

        if (fetchedMovies.isEmpty() && lastViewedMovieData.value != null) {
            movies.add(lastViewedMovieData.value!!)
            isLastViewed.value = true
        }

        movies.addAll(fetchedMovies)
        isLoading.value = false
    }

    // Save last viewed movie and its image URL
    LaunchedEffect(selectedMovie.value) {
        if (selectedMovie.value != null) {
            saveLastViewedMovieInfo(sharedPreferences, selectedMovie.value!!)
        }
    }
}

fun saveLastViewedMovieInfo(sharedPreferences: SharedPreferences, movie: Movie) {
    val gson = Gson()
    val movieDataJson = gson.toJson(movie)
    sharedPreferences.edit().putString("lastViewedMovieData", movieDataJson).apply()
}

fun getLastViewedMovieInfo(sharedPreferences: SharedPreferences): Movie? {
    val movieDataJson = sharedPreferences.getString("lastViewedMovieData", null)
    val gson = Gson()
    return gson.fromJson(movieDataJson, Movie::class.java)
}

// Simulated data fetching
suspend fun fetchMovies(): List<Movie> {
    return withContext(Dispatchers.IO) {
        try {
            val apiService = TmdbApiService.create()
            val response = apiService.getPopularMovies(API_KEY)

            response.results
        } catch (e: Exception) {
            // Handle error
            emptyList()
        }
    }
}


package com.example.movieapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.MovieApp
import com.example.movieapp.ui.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieAppTheme {
                val searchText = remember { mutableStateOf("") }
                MovieApp(
                    searchText = searchText.value,
                    onSearchTextChanged = { filterText ->
                        searchText.value = filterText
                    },
                    viewModel = movieViewModel
                )
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        MovieApp(MovieViewModel(movieRepository = MovieRepository()))
    }
}*/

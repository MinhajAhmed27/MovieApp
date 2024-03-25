package com.example.movieapp.data

import android.content.SharedPreferences
import com.example.movieapp.Constants
import com.example.movieapp.api.TmdbApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun fetchMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbApiService.getPopularMovies(Constants.API_KEY)

                response.results
            } catch (e: Exception) {
                // Handle error
                emptyList()
            }
        }
    }
    fun saveLastViewedMovieInfo(movie:Movie) {
        val gson = Gson()
        val movieDataJson = gson.toJson(movie)
        sharedPreferences.edit().putString("lastViewedMovieData", movieDataJson).apply()
    }

    fun getLastViewedMovieInfo(): Movie {
        val movieDataJson = sharedPreferences.getString("lastViewedMovieData", null)
        val gson = Gson()
        return gson.fromJson(movieDataJson, Movie::class.java)
    }

}
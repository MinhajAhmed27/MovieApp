package com.example.movieapp.api

import androidx.compose.runtime.Immutable

@Immutable
data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val backdrop_path: String
)

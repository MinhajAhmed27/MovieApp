package com.example.movieapp.di

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.ui.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideMovieViewModel(movieRepository: MovieRepository): MovieViewModel {
        return MovieViewModel(movieRepository)
    }
}
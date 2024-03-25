package com.example.movieapp.di

import com.example.movieapp.api.TmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
}
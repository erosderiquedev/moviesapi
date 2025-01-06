package com.erosutuidev.moviesapi.di

import com.erosutuidev.moviesapi.movieList.data.repository.MovieListRepositoryImpl
import com.erosutuidev.moviesapi.movieList.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
}
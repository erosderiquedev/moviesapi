package com.erosutuidev.moviesapi.movieList.presentation

import com.erosutuidev.moviesapi.movieList.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,

    val  popularMovieListPage: Int = 1,
    val upComingMoviesListPage: Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upComingMovieList: List<Movie> = emptyList()

)
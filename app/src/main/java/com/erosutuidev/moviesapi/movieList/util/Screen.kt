package com.erosutuidev.moviesapi.movieList.util

sealed class Screen(val route: String) {
    data object Home : Screen(route = "Main")
    data object PopularMovieList : Screen(route = "popularMovie")
    data object UpcomingMovieList : Screen(route = "upcomingMovie")
    data object Details : Screen(route = "datails")
}
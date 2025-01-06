package com.erosutuidev.moviesapi.movieList.domain.repository

import com.erosutuidev.moviesapi.movieList.domain.model.Movie
import com.erosutuidev.moviesapi.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}
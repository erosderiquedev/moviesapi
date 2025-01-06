package com.erosutuidev.moviesapi.movieList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erosutuidev.moviesapi.movieList.domain.repository.MovieListRepository
import com.erosutuidev.moviesapi.movieList.util.Category
import com.erosutuidev.moviesapi.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {
    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(forceFetchFromRemote = false)
        getUpComingMovieList(forceFetchFromRemote = false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(forceFetchFromRemote = true)
                } else if (event.category == Category.UPCOMING) {
                    getUpComingMovieList(forceFetchFromRemote = true)
                }
            }
        }
    }

    private fun getUpComingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upComingMoviesListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { upComingList ->
                            _movieListState.update {
                                it.copy(
                                    upComingMovieList = movieListState.value.popularMovieList + upComingList.shuffled(),
                                    upComingMoviesListPage = movieListState.value.upComingMoviesListPage + 1
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { it.copy(isLoading = false) }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
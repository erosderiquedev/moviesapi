package com.erosutuidev.moviesapi.movieList.data.repository

import coil.network.HttpException
import com.erosutuidev.moviesapi.movieList.data.local.movie.MovieDatabase
import com.erosutuidev.moviesapi.movieList.data.mappers.toMovie
import com.erosutuidev.moviesapi.movieList.data.mappers.toMovieEntity
import com.erosutuidev.moviesapi.movieList.data.remote.MovieApi
import com.erosutuidev.moviesapi.movieList.data.remote.respond.MovieDto
import com.erosutuidev.moviesapi.movieList.domain.model.Movie
import com.erosutuidev.moviesapi.movieList.domain.repository.MovieListRepository
import com.erosutuidev.moviesapi.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category = category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(data = localMovieList.map { movieEntity ->
                    movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Fetching Movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Fetching Movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Fetching Movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto: MovieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMoviesList(movieEntities)
            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if(movieEntity != null) {
                emit(Resource.Success(movieEntity.toMovie(category = movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("Error no such movie"))
            emit(Resource.Loading(false))
        }
    }
}
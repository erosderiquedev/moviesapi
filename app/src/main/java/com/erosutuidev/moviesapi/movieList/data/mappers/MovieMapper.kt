package com.erosutuidev.moviesapi.movieList.data.mappers

import com.erosutuidev.moviesapi.movieList.data.local.movie.MovieEntity
import com.erosutuidev.moviesapi.movieList.data.remote.respond.MovieDto
import com.erosutuidev.moviesapi.movieList.domain.model.Movie

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_count = vote_count,
        vote_average = vote_average,
        popularity = popularity,
        video = video,
        id = id,
        adult = adult,
        original_title = original_title,
        category = category,
        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }
    )
}

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_count = vote_count ?: -1,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,
        category = category,
        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
                               },
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0
    )
}
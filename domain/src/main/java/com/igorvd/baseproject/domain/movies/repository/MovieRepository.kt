package com.igorvd.baseproject.domain.movies.repository

import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.entities.MovieVideo

/**
 * @author Igor Vilela
 * @since 26/07/18
 */
interface MovieRepository {

    /**
     * Returns a list of movies
     *
     */
    suspend fun getMovies(page: Int, sortBy: MovieSortBy): List<Movie>

    suspend fun getMovieTrailers(movieId: Int): List<MovieVideo>

}
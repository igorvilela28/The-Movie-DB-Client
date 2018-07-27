package com.igorvd.baseproject.domain.movies.repository

import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie

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

}
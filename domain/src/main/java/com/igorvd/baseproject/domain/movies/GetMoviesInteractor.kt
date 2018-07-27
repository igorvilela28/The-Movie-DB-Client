package com.igorvd.baseproject.domain.movies

import com.igorvd.baseproject.domain.Interactor
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import javax.inject.Inject

/**
 *
 * @author Igor Vilela
 * @since 27/07/2018
 */
class GetMoviesInteractor
@Inject
constructor(private val movieRepository: MovieRepository): Interactor<List<Movie>, GetMoviesInteractor.Params> {

    override suspend fun execute(params: Params): List<Movie> {

        return movieRepository.getMovies(params.page, params.sortBy)

    }

    data class Params(val page: Int, val sortBy: MovieSortBy)


}
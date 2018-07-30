package com.igorvd.baseproject.domain.movies

import com.igorvd.baseproject.domain.Interactor
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.entities.MovieVideo
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import javax.inject.Inject

/**
 *
 * @author Igor Vilela
 * @since 30/07/2018
 */
class GetMovieTrailersInteractor
@Inject
constructor(private val movieRepository: MovieRepository): Interactor<List<MovieVideo>, GetMovieTrailersInteractor.Params> {

    override suspend fun execute(params: Params): List<MovieVideo> {

        return movieRepository.getMovieTrailers(params.movieId)

    }

    data class Params(val movieId: Int)


}
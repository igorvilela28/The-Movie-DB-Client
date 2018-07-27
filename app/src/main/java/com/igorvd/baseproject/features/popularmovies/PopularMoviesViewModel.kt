package com.igorvd.baseproject.features.popularmovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import com.igorvd.baseproject.domain.utils.extensions.withIOContext
import com.igorvd.baseproject.features.BaseViewModel
import javax.inject.Inject

/**
 * @author Igor Vilela
 * @since 26/07/18
 */
class PopularMoviesViewModel
@Inject
constructor(private val movieRepository: MovieRepository) : BaseViewModel() {

    var currentPage = 0
    val DEFAULT_SORT_BY = MovieSortBy.POPULARITY

    /**
     * backing field for [movies]
     */
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    suspend fun getMovies(sortBy: MovieSortBy = DEFAULT_SORT_BY) {

        doWorkWithProgress {

            val newPage = currentPage + 1
            val movies = withIOContext {

                movieRepository.getMovies(newPage, sortBy)
            }

            this._movies.value = movies
            currentPage = newPage
        }
    }
}
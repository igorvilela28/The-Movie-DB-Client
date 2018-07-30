package com.igorvd.baseproject.features.popularmovies.listmovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.igorvd.baseproject.domain.movies.GetMoviesInteractor
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.utils.extensions.withIOContext
import com.igorvd.baseproject.features.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Igor Vilela
 * @since 26/07/18
 */
class PopularMoviesViewModel
@Inject
constructor(private val getMoviesInteractor: GetMoviesInteractor) : BaseViewModel() {

    private val DEFAULT_SORT_BY = MovieSortBy.POPULARITY
    var currentSortBy = DEFAULT_SORT_BY
    var currentPage = 0

    /**
     * backing field for [movies]
     */
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun clearCurrentMovies() {
        currentPage = 0
        _movies.value = emptyList()
    }

    /**
     * Use to get a list of movies, based on the [currentPage] and the [sortBy] param
     */
    suspend fun getMovies(sortBy: MovieSortBy) {

        Timber.d("currentPage: $currentPage")

        doWorkWithProgress {

            val newPage = currentPage + 1
            val movies = withIOContext {
                getMoviesInteractor.execute(GetMoviesInteractor.Params(newPage, sortBy))
            }

            val allMovies = this.movies.value?.let { it + movies } ?: movies

            this._movies.value = allMovies
            currentPage = newPage
            currentSortBy = sortBy
        }
    }
}
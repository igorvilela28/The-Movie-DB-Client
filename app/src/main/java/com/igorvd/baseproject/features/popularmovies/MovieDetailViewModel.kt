package com.igorvd.baseproject.features.popularmovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.igorvd.baseproject.domain.movies.GetMovieTrailersInteractor
import com.igorvd.baseproject.domain.movies.GetMoviesInteractor
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.entities.MovieVideo
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import com.igorvd.baseproject.domain.utils.extensions.withIOContext
import com.igorvd.baseproject.features.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Igor Vilela
 * @since 30/07/18
 */
class MovieDetailViewModel
@Inject constructor(private val getMovieTrailersInteractor: GetMovieTrailersInteractor) :
    BaseViewModel() {


    /**
     * backing field for [movies]
     */
    private val _movieTrailers = MutableLiveData<List<MovieVideo>>()
    val movieTrailers: LiveData<List<MovieVideo>> = _movieTrailers

    suspend fun getMovieTrailers(movieId: Int) {

        doWorkWithProgress {

            val trailers = withIOContext {
                val params = GetMovieTrailersInteractor.Params(movieId)
                getMovieTrailersInteractor.execute(params)
            }

            _movieTrailers.value = trailers

        }
    }


}
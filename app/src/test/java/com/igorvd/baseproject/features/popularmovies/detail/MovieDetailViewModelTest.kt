package com.igorvd.baseproject.features.popularmovies.detail

import android.arch.lifecycle.Observer
import com.igorvd.baseproject.BaseViewModelTest
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.exceptions.MyIOException
import com.igorvd.baseproject.domain.exceptions.MyServerErrorException
import com.igorvd.baseproject.domain.movies.GetMovieTrailersInteractor
import com.igorvd.baseproject.domain.movies.entities.MovieVideo
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author Igor Vilela
 * @since 31/07/2018
 */
class MovieDetailViewModelTest: BaseViewModelTest<MovieDetailViewModel>() {

    @MockK
    private lateinit var getMovieTrailersInteractor: GetMovieTrailersInteractor

    @MockK
    private lateinit var observerMovieTrailers: Observer<List<MovieVideo>>

    @Before
    override fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mViewModel = MovieDetailViewModel(getMovieTrailersInteractor)
        mViewModel.movieTrailers.observeForever(observerMovieTrailers)
        super.setUp()
    }

    @After
    override fun tearDown() {
        mViewModel.movieTrailers.removeObserver(observerMovieTrailers)
        super.tearDown()
    }

    @Test
    fun `load movie trailers successfuly`() = runBlocking {

        val trailers = generateMovieTrailer().generateList()

        val params = GetMovieTrailersInteractor.Params(1)
        coEvery { getMovieTrailersInteractor.execute(params) } returns trailers

        mViewModel.loadMovieTrailers(1)

        verify(exactly = 1) {
            observerMovieTrailers.onChanged(trailers)
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
        }

        verify { observerShowError wasNot Called }

    }

    @Test
    fun `load movie trailers should notify about network error`() = runBlocking {

        val params = GetMovieTrailersInteractor.Params(1)
        coEvery { getMovieTrailersInteractor.execute(params) } throws MyIOException("error")

        mViewModel.loadMovieTrailers(1)

        verify(exactly = 1) {
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
            observerShowError.onChanged(R.string.error_fail_connection)
        }

        verify { observerMovieTrailers wasNot Called }

    }

    @Test
    fun `load movie trailers should notify about server error`() = runBlocking {

        val params = GetMovieTrailersInteractor.Params(1)
        coEvery { getMovieTrailersInteractor.execute(params) } throws MyServerErrorException("error")

        mViewModel.loadMovieTrailers(1)

        verify(exactly = 1) {
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
            observerShowError.onChanged(R.string.error_unknown)
        }

        verify { observerMovieTrailers wasNot Called }

    }

    private fun generateMovieTrailer(): MovieVideo {

        return MovieVideo(
            movieId = 1,
            name = "Trailer 1",
            url = "https://www.youtube.com/watch?v=SUXWAEX2jlg"
        )
    }
}
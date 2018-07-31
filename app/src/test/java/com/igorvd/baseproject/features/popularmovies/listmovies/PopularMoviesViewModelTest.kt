package com.igorvd.baseproject.features.popularmovies.listmovies

import android.arch.lifecycle.Observer
import com.igorvd.baseproject.BaseViewModelTest
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.exceptions.MyIOException
import com.igorvd.baseproject.domain.exceptions.MyServerErrorException
import com.igorvd.baseproject.domain.movies.GetMoviesInteractor
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author Igor Vilela
 * @since 31/07/2018
 */
class MovieDetailViewModelTest: BaseViewModelTest<PopularMoviesViewModel>() {

    @MockK
    private lateinit var getMoviesInteractor: GetMoviesInteractor

    @MockK
    private lateinit var observerMovies: Observer<List<Movie>>

    @Before
    override fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mViewModel = PopularMoviesViewModel(getMoviesInteractor)
        mViewModel.movies.observeForever(observerMovies)
        super.setUp()
    }

    @After
    override fun tearDown() {
        mViewModel.movies.removeObserver(observerMovies)
        super.tearDown()
    }

    @Test
    fun `clear current movies`() {

        mViewModel.currentPage = 5
        mViewModel.currentSortBy = MovieSortBy.VOTE_AVERAGE

        mViewModel.clearCurrentMovies()
        Assert.assertEquals(0, mViewModel.currentPage)

        verify(exactly = 1) {
            observerMovies.onChanged(emptyList())
        }

        verify { listOf(observerHideProgress, observerShowProgress, observerShowError) wasNot Called }
    }

    @Test
    fun `load movies successfuly`() = runBlocking {

        val movies = generateMovie().generateList()

        val params = GetMoviesInteractor.Params(1, MovieSortBy.POPULARITY)
        coEvery { getMoviesInteractor.execute(params) } returns movies

        mViewModel.getMovies(mViewModel.currentSortBy)

        verify(exactly = 1) {
            observerMovies.onChanged(movies)
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
        }

        verify { observerShowError wasNot Called }

        Assert.assertEquals(1, mViewModel.currentPage)
        Assert.assertEquals(MovieSortBy.POPULARITY, mViewModel.currentSortBy)

    }

    @Test
    fun `load movie trailers should notify about network error`() = runBlocking {

        val params = GetMoviesInteractor.Params(1, MovieSortBy.POPULARITY)
        coEvery { getMoviesInteractor.execute(params) } throws MyIOException("error")

        mViewModel.getMovies(mViewModel.currentSortBy)

        verify(exactly = 1) {
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
            observerShowError.onChanged(R.string.error_fail_connection)
        }

        verify { observerMovies wasNot Called }

        Assert.assertEquals(0, mViewModel.currentPage)
        Assert.assertEquals(MovieSortBy.POPULARITY, mViewModel.currentSortBy)

    }

    @Test
    fun `load movie trailers should notify about server error`() = runBlocking {

        val params = GetMoviesInteractor.Params(1, MovieSortBy.POPULARITY)
        coEvery { getMoviesInteractor.execute(params) } throws MyServerErrorException("error")

        mViewModel.getMovies(mViewModel.currentSortBy)

        verify(exactly = 1) {
            observerShowProgress.onChanged(null)
            observerHideProgress.onChanged(null)
            observerShowError.onChanged(R.string.error_unknown)
        }

        verify { observerMovies wasNot Called }

        Assert.assertEquals(0, mViewModel.currentPage)
        Assert.assertEquals(MovieSortBy.POPULARITY, mViewModel.currentSortBy)

    }

    private fun generateMovie(): Movie {

        return Movie(
            id = 1,
            adult = true,
            backdropUrl = "http://www.google.com",
            genres = listOf("Action", "Comedy"),
            originalLanguage = "English",
            originalTitle = "Deadpool",
            overview = "",
            popularity = 10.0,
            posterUrl = "http://www.google.com",
            releaseDate = "2016-04-01",
            title = "Deadpool",
            video = true,
            voteAverage = 10.0,
            voteCount = 100000
        )
    }
}
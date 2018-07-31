package com.igorvd.baseproject.domain.movies

import com.igorvd.baseproject.domain.movies.entities.MovieVideo
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Igor Vilela
 * @since 31/07/2018
 */
class GetMovieTrailersInteractorTest {


    private lateinit var getMovieTrailersInteractor: GetMovieTrailersInteractor

    @MockK
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {

        MockKAnnotations.init(this)
        getMovieTrailersInteractor = GetMovieTrailersInteractor(movieRepository)
    }


    @Test
    fun `should get movies successfuly`() = runBlocking {

        val expectedTrailers = listOf(generateMovieTrailer())
        coEvery { movieRepository.getMovieTrailers(1) } returns expectedTrailers

        val params = GetMovieTrailersInteractor.Params(1)
        val trailers = getMovieTrailersInteractor.execute(params)

        assertEquals(expectedTrailers, trailers)
    }

    private fun generateMovieTrailer(): MovieVideo {

        return MovieVideo(
            movieId = 1,
            name = "Trailer 1",
            url = "https://www.youtube.com/watch?v=SUXWAEX2jlg"
        )
    }

}
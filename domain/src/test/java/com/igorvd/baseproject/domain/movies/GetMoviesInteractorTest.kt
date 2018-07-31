package com.igorvd.baseproject.domain.movies

import com.igorvd.baseproject.domain.movies.entities.Movie
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
class GetMoviesInteractorTest {


    private lateinit var getMoviesInteractor: GetMoviesInteractor

    @MockK
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {

        MockKAnnotations.init(this)
        getMoviesInteractor = GetMoviesInteractor(movieRepository)
    }


    @Test
    fun `should get movies successfuly`() = runBlocking {

        val expectedMovies = listOf(generateMovie())
        coEvery { movieRepository.getMovies(1, MovieSortBy.POPULARITY) } returns expectedMovies

        val params = GetMoviesInteractor.Params(1, MovieSortBy.POPULARITY)
        val movies = getMoviesInteractor.execute(params)

        assertEquals(expectedMovies, movies)
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
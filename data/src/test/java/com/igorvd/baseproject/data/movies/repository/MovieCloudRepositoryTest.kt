package com.igorvd.baseproject.data.movies.repository

import com.igorvd.baseproject.data.network.ApiClientBuilder
import com.igorvd.baseproject.data.network.MovieDbApi
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.prosegur.genesis.mobile.data.utils.enqueue200Response
import com.prosegur.genesis.mobile.data.utils.loadJsonFromResources
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author Igor Vilela
 * @since 31/07/2018
 */
class MovieCloudRepositoryTest {

    private lateinit var server: MockWebServer
    private lateinit var movieDbApi: MovieDbApi
    private lateinit var movieCloudRepository: MovieCloudRepository
    private lateinit var classLoader: ClassLoader

    @Before
    fun setUp() {

        server = MockWebServer()
        server.start()
        val url = server.url("/").toString()

        movieDbApi = ApiClientBuilder.createService(MovieDbApi::class.java, url)

        movieCloudRepository = MovieCloudRepository(movieDbApi)
        classLoader = javaClass.classLoader
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `load movies`() = runBlocking {

        //region: arrange

        val moviesResponse = loadJsonFromResources(
            javaClass.classLoader,
            "MoviesResponse.json")
        server.enqueue200Response(moviesResponse)

        val configurationResponse = loadJsonFromResources(
            javaClass.classLoader,
            "ConfigurationResponse.json")
        server.enqueue200Response(configurationResponse)

        val genresResponse = loadJsonFromResources(
        javaClass.classLoader,
        "GenresResponse.json")
        server.enqueue200Response(genresResponse)

        //endregion

        //region: act

        val movies = movieCloudRepository.getMovies(1, MovieSortBy.POPULARITY)

        //endregion

        //region: assert

        assertTrue(movies.isNotEmpty())

        val movie = movies[0]

        assertEquals(249, movie.voteCount)
        assertEquals(353081, movie.id)
        assertEquals(false, movie.video)
        assertEquals(7.5, movie.voteAverage)
        assertEquals("Missao: Impossivel - Efeito Fallout", movie.title)
        assertEquals(465.786, movie.popularity)
        assertEquals("https://image.tmdb.org/t/p/w92/kVv60GGRuh3kF6KI5Hn2T7QWWx0.jpg", movie.posterUrl)
        assertEquals("Mission: Impossible - Fallout", movie.originalTitle)
        assertEquals(listOf("Aventura", "Acao", "Thriller"), movie.genres)
        assertEquals("https://image.tmdb.org/t/p/w300/5qxePyMYDisLe8rJiBYX8HKEyv2.jpg", movie.backdropUrl)
        assertEquals(false, movie.adult)
        assertEquals("Quando uma importante missao nao sai como o planejado, Ethan Hunt (Tom Cruise) salvao  mundo", movie.overview)
        assertEquals("2018-07-25", movie.releaseDate)

        //endregion

    }


}
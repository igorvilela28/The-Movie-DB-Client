package com.igorvd.baseproject.data.movies.repository

import com.igorvd.baseproject.data.network.ApiClientBuilder
import com.igorvd.baseproject.data.network.MovieDbApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

/**
 * @author Igor Vilela
 * @since 31/07/2018
 */
class MovieCloudRepositoryTest {

    protected lateinit var server: MockWebServer
    protected lateinit var genesisApi: MovieDbApi

    @Before
    fun setUp() {

        server = MockWebServer()
        server.start()

        val url = server.url("/").toString()
        genesisApi = ApiClientBuilder.createService(MovieDbApi::class.java, url)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }


}
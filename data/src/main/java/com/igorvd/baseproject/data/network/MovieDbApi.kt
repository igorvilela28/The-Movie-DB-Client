package com.igorvd.baseproject.data.network

import com.igorvd.baseproject.data.movies.entities.Configuration
import com.igorvd.baseproject.data.movies.entities.GenreResult
import com.igorvd.baseproject.data.movies.entities.MovieResult
import com.igorvd.baseproject.data.movies.entities.VideoResult
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Igor Vilela
 * @since 10/01/2018
 */


interface MovieDbApi {

    @GET("configuration")
    fun getConfiguration(): Call<Configuration>

    //popularity.desc , vote_average.desc

    @GET("movie/popular")
    fun getPopularMovies(
            @Query("page") page: Int,
            @Query("sort_by") sortBy: String = "popularity.desc"
    ): Call<MovieResult>


    @GET("genre/movie/list")
    fun getMoviesGenres(): Call<GenreResult>


    @GET("movie/{movieId}/videos")
    fun getMovieVideos(@Path("movieId") movieId: Int): Call<VideoResult>

}

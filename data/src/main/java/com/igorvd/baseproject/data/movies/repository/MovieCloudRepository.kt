package com.igorvd.baseproject.data.movies.repository

import com.igorvd.baseproject.data.movies.entities.*
import com.igorvd.baseproject.data.network.MovieDbApi
import com.igorvd.baseproject.data.network.requests.SynchronousRequestManagerImpl
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Igor Vilela
 * @since 26/07/18
 */
class MovieCloudRepository @Inject constructor(private val movieDbApi: MovieDbApi) : MovieRepository {

    private var configuration: Configuration? = null
    private var genres: List<GenreSchema>? = null

    private val requestConfiguration = SynchronousRequestManagerImpl<Configuration>()
    private val requestGenres = SynchronousRequestManagerImpl<GenreResult>()
    private val requestMovies = SynchronousRequestManagerImpl<MovieResult>()


    override suspend fun getMovies(page: Int, sortBy: MovieSortBy): List<Movie> {

        val call = movieDbApi.getPopularMovies(page, sortBy.value)

        val moviesSchema = requestMovies.getResult(call).movies
        val configuration = getConfiguration()
        val genres = getGenres()

        return moviesSchema.map { it.toMovie(configuration, genres) }

    }

    /**
     * Transforms from [MovieSchema] to [Movie]
     */
    fun MovieSchema.toMovie(configuration: Configuration, genresSchema: List<GenreSchema>): Movie {

        val posterUrl: String = this.getPosterUrl(configuration)
        val backdropUrl: String = this.getBackdropUrl(configuration)
        val genres = this.getGenres(genresSchema)

        return Movie(
                voteCount = voteCount,
                id = id,
                video = video,
                voteAverage = voteAverage,
                title = title,
                popularity = popularity,
                posterUrl = posterUrl,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                genres = genres,
                backdropUrl = backdropUrl,
                adult = adult,
                overview = overview,
                releaseDate = releaseDate
        )

    }

    /**
     * Get the cached configuration, if not present, fetches for it
     */
    private suspend fun getConfiguration(): Configuration {
        return configuration?.let { it } ?: run {
            val call = movieDbApi.getConfiguration()
            val configuration = requestConfiguration.getResult(call)
            this.configuration = configuration
            configuration
        }
    }

    /**
     * Get the cached list of genres, if not present, fetches for a list
     */
    private suspend fun getGenres(): List<GenreSchema> {
        return genres?.let { it } ?: run {
            val call = movieDbApi.getMoviesGenres()
            val genres = requestGenres.getResult(call).genres
            this.genres = genres
            genres
        }
    }

    /**
     * Gets the poster URL based on the path
     */
    private fun MovieSchema.getPosterUrl(configuration: Configuration): String {

        val path = posterPath
        return with(configuration.images) {
            "$secureBaseUrl/${posterSizes.last()}/$path"
        }
    }

    /**
     * Gets the backdrop URL based on the path
     */
    private fun MovieSchema.getBackdropUrl(configuration: Configuration): String {

        val path = backdropPath
        return with(configuration.images) {
            "$secureBaseUrl/${backdropSizes.last()}/$path"
        }
    }

    /**
     * Gets the genres titles based on their ids
     */
    private fun MovieSchema.getGenres(genresSchema: List<GenreSchema>): List<String> {
        return genreIds.map { id -> genresSchema.firstOrNull { it.id == id }?.name }.filterNotNull()
    }
}
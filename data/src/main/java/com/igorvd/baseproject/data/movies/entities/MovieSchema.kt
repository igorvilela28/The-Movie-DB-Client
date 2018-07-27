package com.igorvd.baseproject.data.movies.entities
import com.google.gson.annotations.SerializedName


/**
 * @author Igor Vilela
 * @since 26/07/18
 */





data class MovieResult(
    @SerializedName("page") var page: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("results") var movies: List<MovieSchema>
)

data class MovieSchema(
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("title") var title: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("genre_ids") var genreIds: List<Int>,
    @SerializedName("backdrop_path") var backdropPath: String,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("overview") var overview: String,
    @SerializedName("release_date") var releaseDate: String
)
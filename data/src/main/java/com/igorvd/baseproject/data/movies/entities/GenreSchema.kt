package com.igorvd.baseproject.data.movies.entities
import com.google.gson.annotations.SerializedName


/**
 * @author Igor Vilela
 * @since 26/07/18
 */



data class GenreResult(
    @SerializedName("genres") val genres: List<GenreSchema>
)

data class GenreSchema(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
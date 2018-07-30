package com.igorvd.baseproject.data.movies.entities
import com.google.gson.annotations.SerializedName


/**
 * @author Igor Vilela
 * @since 26/07/18
 */


data class VideoResult(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val videos: List<VideoSchema>
)

data class VideoSchema(
    @SerializedName("id") val id: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
)
package com.igorvd.baseproject.data.movies.entities
import com.google.gson.annotations.SerializedName


/**
 * @author Igor Vilela
 * @since 26/07/18
 */




data class Configuration(
    @SerializedName("images") val images: Images,
    @SerializedName("change_keys") val changeKeys: List<String>
)

data class Images(
    @SerializedName("base_url") val baseUrl: String,
    @SerializedName("secure_base_url") val secureBaseUrl: String,
    @SerializedName("backdrop_sizes") val backdropSizes: List<String>,
    @SerializedName("logo_sizes") val logoSizes: List<String>,
    @SerializedName("poster_sizes") val posterSizes: List<String>,
    @SerializedName("profile_sizes") val profileSizes: List<String>,
    @SerializedName("still_sizes") val stillSizes: List<String>
)
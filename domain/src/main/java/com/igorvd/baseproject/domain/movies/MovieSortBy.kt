package com.igorvd.baseproject.domain.movies

/**
 * @author Igor Vilela
 * @since 26/07/18
 */
enum class MovieSortBy(val value: String) {

    POPULARITY("popularity.desc"),
    VOTE_AVERAGE("vote_average.desc")

}
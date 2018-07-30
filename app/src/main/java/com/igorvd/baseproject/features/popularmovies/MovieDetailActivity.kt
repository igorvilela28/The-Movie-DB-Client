package com.igorvd.baseproject.features.popularmovies

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.utils.extensions.toStringWithSeparator
import com.igorvd.baseproject.utils.extensions.content
import com.igorvd.baseproject.utils.extensions.extra
import com.igorvd.baseproject.utils.extensions.loadImageFromUrl
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private val movie by extra<Movie>(EXTRA_MOVIE)

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movie: Movie): Intent {

            val it = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }
            return it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.title = movie.title

        expandedImage.loadImageFromUrl(this, movie.backdropUrl)
        tvTitle.content = movie.title
        overview.content = movie.overview
        tagline.content = movie.genres.toStringWithSeparator(", ")
        year.content = movie.releaseDate

    }
}

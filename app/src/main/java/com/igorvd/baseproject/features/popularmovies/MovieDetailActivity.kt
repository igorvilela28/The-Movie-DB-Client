package com.igorvd.baseproject.features.popularmovies

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.utils.extensions.toStringWithSeparator
import com.igorvd.baseproject.utils.ViewModelFactory
import com.igorvd.baseproject.utils.extensions.content
import com.igorvd.baseproject.utils.extensions.extra
import com.igorvd.baseproject.utils.extensions.loadImageFromUrl
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.util.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
    }

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
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setupToolbar()
        setupMovieData()

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupMovieData() {
        expandedImage.loadImageFromUrl(this, movie.backdropUrl)
        tvTitle.content = movie.title
        tvOverview.content = movie.overview
        tvGenres.content = movie.genres.toStringWithSeparator(", ")
        tvReleaseDate.content = movie.releaseDate
        tvOriginalLanguage.content = movie.originalLanguage
        tvVoteAverage.content = "${movie.voteAverage} / 10.0"
    }
}

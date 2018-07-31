package com.igorvd.baseproject.features.popularmovies.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.domain.utils.extensions.launchUI
import com.igorvd.baseproject.domain.utils.extensions.toStringWithSeparator
import com.igorvd.baseproject.utils.ViewModelFactory
import com.igorvd.baseproject.utils.extensions.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.default_error.*
import kotlinx.android.synthetic.main.movie_trailers.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    private val adapter =
        MovieVideosAdapter(onItemClicked = {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
        })

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

    //**************************************************************************
    // region: LIFE CYCLE
    //**************************************************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setupToolbar()
        setupMovieData()
        setupRv()
        setupObservers()

        if (viewModel.movieTrailers.value == null) {
            loadTrailers()
        }

    }

    //endregion

    //**************************************************************************
    // region: INNER METHODS
    //**************************************************************************

    private fun loadTrailers() {

        launchUI {
            viewModel.loadMovieTrailers(movie.id)
        }

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

    private fun setupRv() {

        rvTrailers.setup(
            context = this,
            adapter = adapter
        )

        rvTrailers.isNestedScrollingEnabled = false

        btnTryAgain.setOnClickListener { loadTrailers() }
    }

    private fun setupObservers() {

        viewModel.observe(this, ::showProgress, ::hideProgress, ::showError)


        viewModel.movieTrailers.observeNotNull(this) { videos ->

            adapter.submitList(videos)

            tvError.isVisible = videos.isEmpty()
            if (videos.isEmpty()) {
                tvError.content = getString(R.string.empty_content)
            }
        }
    }

    private fun showProgress() {

        tvError.isVisible = false
        btnTryAgain.isVisible = false
        progressBar.isVisible = true

    }

    private fun hideProgress() {
        progressBar.isVisible = false
    }

    private fun showError(stringRes: Int) {
        btnTryAgain.isVisible = true
        tvError.isVisible = true
        tvError.content = getString(stringRes)
    }

    //endregion
}

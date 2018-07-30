package com.igorvd.baseproject.features.popularmovies.listmovies

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.utils.extensions.launchUI
import com.igorvd.baseproject.utils.EndlessRecyclerViewScrollListener
import com.igorvd.baseproject.utils.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_popular_movies.*
import kotlinx.android.synthetic.main.default_error.*
import timber.log.Timber
import javax.inject.Inject
import android.view.Menu
import android.widget.AdapterView
import android.widget.Spinner
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.features.popularmovies.detail.MovieDetailActivity
import com.igorvd.baseproject.utils.adapter.SpinnerDropdownAdapter
import com.igorvd.baseproject.utils.extensions.*
import kotlinx.android.synthetic.main.app_bar_layout.*


class PopularMoviesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
                .get(PopularMoviesViewModel::class.java)
    }

    private val LIST_STATE_KEY = "recycler_state"
    private var listState: Parcelable? = null

    private val adapter by lazy {
        MoviesAdapter(onItemClicked = { movie ->
            val it =
                MovieDetailActivity.newIntent(
                    this,
                    movie
                )
            startActivity(it)
        }, onRetryClick = { loadMovies() })
    }

    private val spanCount by lazy {
        val orientation = getResources().getConfiguration().orientation
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT  -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
    }

    private val staggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }

    private val scrollListener: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadMovies()
            }
        }
    }

    //**************************************************************************
    // region: LIFE CYCLE
    //**************************************************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)

        savedInstanceState?.let {
            restoreInstance(it)
        }

        setupToolbar()

        setupRv()
        setupObservers()

        if (viewModel.movies.value == null) {
            loadMovies()
        }

    }

    override fun onResume() {
        super.onResume()

        listState?.let { staggeredGridLayoutManager.onRestoreInstanceState(it) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        listState = staggeredGridLayoutManager.onSaveInstanceState()
        outState?.putParcelable(LIST_STATE_KEY, listState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.popular_movies_menu, menu)
        val spinner = menu.findItem(R.id.spinner).actionView as Spinner
        setupFilterSpinner(spinner)

        return true
    }

    //endregion


    //**************************************************************************
    // region: INNER METHODS
    //**************************************************************************

    private fun restoreInstance(savedInstanceState: Bundle) {

        listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.popular_movies_title)
    }

    private fun setupRv() {

        adapter.setHasStableIds(true)
        with(rvPosters) {
            addOnScrollListener(scrollListener)
            setup(
                    context = this@PopularMoviesActivity,
                    layoutManager = staggeredGridLayoutManager,
                    adapter = this@PopularMoviesActivity.adapter)
        }

        btnTryAgain.setOnClickListener { loadMovies() }
    }

    private fun setupObservers() {

        viewModel.movies.observeNotNull(this, {

            if(adapter.hasFooter) {
                adapter.removeFooter()
            }
            adapter.submitList(it)
        })

        viewModel.observe(this, ::showProgress, ::hideProgress, ::showError)
    }

    private fun loadMovies(movieSortBy: MovieSortBy = viewModel.currentSortBy) {
        launchUI { viewModel.getMovies(sortBy = movieSortBy) }
    }

    private fun setupFilterSpinner(spinner: Spinner) {

        val adapter = SpinnerDropdownAdapter(mContext = this,
            items = MovieSortBy.values().toMutableList(),
            getText = { searchBy ->
                when (searchBy) {
                    MovieSortBy.POPULARITY -> getString(R.string.popularity)
                    MovieSortBy.VOTE_AVERAGE -> getString(R.string.best_rating)
                }
            })

        spinner.adapter = adapter
        val listener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val sortBy = spinner.getSelectedItemOrNull<MovieSortBy>()

                sortBy?.let {
                    if (viewModel.currentSortBy != it) {
                        viewModel.clearCurrentMovies()
                        loadMovies(it)
                    }
                }
            }
        }

        spinner.setSelectionListenerWithoutNotify(listener)
    }

    private fun showProgress() {

        btnTryAgain.isVisible = false
        tvError.isVisible = false

        val size = viewModel.movies.value?.size ?: 0
        if (size > 0) {
            adapter.showFooterProgress()
        } else {
            progressBar.isVisible = true
        }
    }

    private fun hideProgress() {

        progressBar.isVisible = false

    }

    private fun showError(stringRes: Int) {

        val size = viewModel.movies.value?.size ?: 0
        if (size > 0) {

            adapter.showFooterError()

        } else {

            btnTryAgain.isVisible = true
            tvError.isVisible = true
            tvError.content = getString(stringRes)
        }

    }
}

//endregion

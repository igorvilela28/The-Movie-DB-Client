package com.igorvd.baseproject.features.popularmovies

import android.app.Activity
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
import com.igorvd.baseproject.utils.extensions.observeNotNull
import com.igorvd.baseproject.utils.extensions.setup
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.default_error.*
import timber.log.Timber
import javax.inject.Inject
import android.R.array
import android.widget.ArrayAdapter
import android.support.v4.view.MenuItemCompat.getActionView
import android.view.Menu
import android.widget.Spinner
import com.igorvd.baseproject.domain.movies.MovieSortBy
import com.igorvd.baseproject.utils.adapter.SpinnerDropdownAdapter


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
        MoviesAdapter(
            onItemClicked = {},
            onRetryClick = ::loadMovies
        )
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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.popular_movies_title)
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

        val item = menu.findItem(R.id.spinner)
        val spinner = item.getActionView() as Spinner

        val adapter = SpinnerDropdownAdapter<MovieSortBy>(
            mContext = this,
            items = MovieSortBy.values().toMutableList(),
            getText = {searchBy ->
                when (searchBy) {
                    MovieSortBy.POPULARITY -> "Popularidade"
                    MovieSortBy.VOTE_AVERAGE -> "Melhor avaliados"
                }
            }
        )

        spinner.adapter = adapter

        return true
    }

    private fun restoreInstance(savedInstanceState: Bundle) {

        listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
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


    }

    private fun loadMovies() {
        launchUI { viewModel.getMovies() }
    }

    private fun setupObservers() {

        viewModel.movies.observeNotNull(this, {

            if(adapter.hasFooter) {
                adapter.removeFooter()
            }

            adapter.submitList(it)

            Timber.d("url: ${it.first().posterUrl}")

        })

        viewModel.observe(this, ::showProgress, ::hideProgress)
    }

    fun showProgress() {

        btnTryAgain.visibility = View.GONE
        tvError.visibility = View.GONE

        val size = viewModel.movies.value?.size ?: 0
        if (size > 0) {
            adapter.showFooterProgress()
        } else {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {

        progressBar.visibility = View.GONE

    }

    fun showError() {

        val size = viewModel.movies.value?.size ?: 0
        if (size > 0) {

            adapter.showFooterError()

        } else {

            btnTryAgain.visibility = View.VISIBLE
            tvError.visibility = View.VISIBLE
        }

    }
}

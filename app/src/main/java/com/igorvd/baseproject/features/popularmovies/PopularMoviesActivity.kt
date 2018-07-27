package com.igorvd.baseproject.features.popularmovies

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.utils.extensions.launchUI
import com.igorvd.baseproject.utils.ViewModelFactory
import com.igorvd.baseproject.utils.extensions.observeNotNull
import com.igorvd.baseproject.utils.extensions.setup
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class PopularMoviesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)
            .get(PopularMoviesViewModel::class.java)
    }

    val adater = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        rvPosters.setup(
                context = this,
                layoutManager = staggeredGridLayoutManager,
                adapter = adater)


        viewModel.movies.observeNotNull(this, {

            adater.movies = it

        })

        launchUI { viewModel.getMovies() }

    }

}

package com.igorvd.baseproject.di.features

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.igorvd.baseproject.data.movies.repository.MovieCloudRepository
import com.igorvd.baseproject.di.core.ViewModelKey
import com.igorvd.baseproject.domain.movies.repository.MovieRepository
import com.igorvd.baseproject.features.popularmovies.MovieDetailViewModel
import com.igorvd.baseproject.features.popularmovies.PopularMoviesViewModel
import com.igorvd.baseproject.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Igor Vilela
 * @since 26/07/18
 */

@Module
abstract class MovieModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun movieRepository(movieCloudRepository: MovieCloudRepository): MovieRepository

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    abstract fun popularMoviesViewMod(viewModel: PopularMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun movieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}
package com.igorvd.baseproject.di.data


import com.google.gson.Gson
import com.igorvd.baseproject.data.network.ApiClientBuilder
import com.igorvd.baseproject.data.network.BASE_URL
import com.igorvd.baseproject.data.network.MovieDbApi
import com.igorvd.baseproject.data.network.interceptor.MovieInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 *
 * @author Jose Martins
 * @since 09/01/2018
 */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesMovieDbApi(movieDbInterceptor: MovieInterceptor): MovieDbApi {

        return ApiClientBuilder
                .createService(
                        MovieDbApi::class.java,
                        BASE_URL,
                        Gson(),
                        movieDbInterceptor)

    }

}
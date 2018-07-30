package com.igorvd.baseproject.data.network.interceptor

import com.igorvd.baseproject.data.BuildConfig
import com.igorvd.baseproject.domain.utils.extensions.getLanguageCode
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author Igor Vilela
 * @since 26/07/18
 */


class MovieInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.ApiKey)
                .addQueryParameter("language", "pt-BR")
                .build()
        val request = original.newBuilder().url(url).build()

        return chain.proceed(request)

    }

}
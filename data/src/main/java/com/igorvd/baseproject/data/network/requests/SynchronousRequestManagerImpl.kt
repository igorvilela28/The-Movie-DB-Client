package com.igorvd.baseproject.data.network.requests

import com.igorvd.baseproject.domain.exceptions.MyException
import com.igorvd.baseproject.domain.exceptions.MyIOException
import com.igorvd.baseproject.domain.exceptions.MyServerErrorException
import retrofit2.Call
import retrofit2.Response
import ru.gildor.coroutines.retrofit.awaitResponse
import timber.log.Timber
import java.io.IOException


/**
 * @author Igor Vilela
 * @since 13/10/17
 */
class SynchronousRequestManagerImpl<T>: SynchronousRequestManager<T> {

    @Throws(MyException::class)
    override suspend fun getResult(call: Call<T>): T {

        try {

            val response: Response<T> = call.awaitResponse()
            return parseResponse(call, response)

        } catch (e: IOException) {

            val url = call.request().url()?.toString()
            val message = if(url != null) "IOError in call, url: $url" else "IOError in call"
            Timber.d(message)

            throw MyIOException(message, e)

        } catch (e: Exception) {

            //we are only retrowing it to be more clear when debugging
            Timber.e(e, "Exception %s on SyncRequestManagerImpl", e.javaClass.simpleName)
            throw e
        }

    }

    @Throws(MyException::class)
    private fun parseResponse(call: Call<T>, response: Response<T>): T {

        val url = call.request().url()?.toString()

        if (response.isSuccessful) {

            Timber.d("call completed successfuly for url: $url")

            return response.body()!!

        } else {

            val message = if(url != null) "Http error, code: ${response.code()}, url: $url"
            else "Http error, code: ${response.code()}"

            throw MyServerErrorException(message)

            //TODO: altera quando precisarmos tratar códigos de erro http em separado.
        }
    }
}
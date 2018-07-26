package com.igorvd.baseproject.data.network.requests

import com.igorvd.baseproject.domain.exceptions.MyException
import retrofit2.Call


/**
 * Makes a synchronous request for some data
 * @author Igor Vilela
 * @since 13/10/17
 */

interface SynchronousRequestManager<T> {

    @Throws(MyException::class)
    suspend fun getResult(call: Call<T>): T

}
package com.prosegur.genesis.mobile.data.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import java.util.concurrent.TimeUnit

/**
 * Useful enqueues to mock our web server responses
 * @author Igor Vilela
 * @since 18/01/18
 */

const val BODY_DELAY = 0L


fun MockWebServer.enqueue200Response(body: String) {

    enqueue(MockResponse()
            .setResponseCode(200)
            .setBodyDelay(BODY_DELAY, TimeUnit.SECONDS)
            .setBody(body))

}

fun MockWebServer.enqueueIOError(body: String) {

    //we use a long delay to simulate a timeout
    enqueue(MockResponse()
            .setResponseCode(200)
            .setBodyDelay(BODY_DELAY, TimeUnit.SECONDS)
            .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST)
            .setBody(body))

}

fun MockWebServer.enqueueBadRequestError() {

    enqueue(MockResponse()
            .setResponseCode(400)
            .setBodyDelay(BODY_DELAY, TimeUnit.SECONDS)
            .setBody("{\n" +
                    "\t\"code\": 400,\n" +
                    "\t\"message\": \"Not found\"\n" +
                    "}"))
}

fun MockWebServer.enqueueStatusOkResponse() {

    enqueue200Response("{\"status\":\"Ok\"}")

}
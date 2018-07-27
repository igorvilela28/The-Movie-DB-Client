package com.igorvd.baseproject.domain.utils.extensions

import android.os.AsyncTask
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

/**
 *
 * @author Igor Vilela
 * @since 05/04/2018
 */

val ioDispatcher: CoroutineDispatcher
    get() = try {
        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
    } catch (e: Exception) {
        /* IMPORTANTE: Em testes unitários não é possível depender de uma implementação do Framework do
         * Android, e por isso, adicionamos o try-catch e estamos utilizando o [CommonPool] como
          * dispatch para casos em que o executor do AsyncTask não está disponível*/
        CommonPool
    }

/**
 * Inicia uma nova coroutine utilizando o builder [launch] com o contexto [UI]
 */
fun launchUI(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(UI, block = block)
}

suspend fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {

    return async(ioDispatcher, block = block)

}

suspend fun <T> withIOContext (block: suspend () -> T): T {

    return withContext(ioDispatcher, block = block)

}

/**
 * Util para podermos cancelar coroutines e pegar exceções genericas, visto que quando uma
 * coroutine é cancelada, é lançada um [CancellationException].
 *
 * [Saiba mais](https://medium.com/@andrea.bresolin/playing-with-kotlin-in-android-coroutines-and-how-to-get-rid-of-the-callback-hell-a96e817c108b)
 */
suspend fun CoroutineScope.tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
    try {
        tryBlock()
    } catch (e: Throwable) {
        if (e !is CancellationException ||
                handleCancellationExceptionManually) {
            catchBlock(e)
        } else {
            throw e
        }
    }
}


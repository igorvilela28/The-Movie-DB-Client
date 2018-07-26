package com.igorvd.baseproject.utils.extensions

import com.igorvd.baseproject.BuildConfig


/**
 *
 * @author Igor Vilela
 * @since 12/07/2018
 */



inline fun runWhenDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}
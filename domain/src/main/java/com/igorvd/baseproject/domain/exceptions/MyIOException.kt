package com.igorvd.baseproject.domain.exceptions

/**
 *
 * @author Igor Vilela
 * @since 11/01/2018
 */
class MyIOException: MyException {

    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)

}
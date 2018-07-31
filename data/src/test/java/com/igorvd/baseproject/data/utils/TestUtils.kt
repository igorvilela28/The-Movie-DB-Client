package com.prosegur.genesis.mobile.data.utils

import com.igorvd.baseproject.data.utils.readContent
import java.io.File

/**
 *
 * @author Igor Vilela
 * @since 18/01/2018
 */


/**
 * Loads the given json into a String. The json file must exists in the /tests/resources directory
 */
fun loadJsonFromResources(classLoader: ClassLoader, fileName: String): String {

    val url = classLoader.getResource(fileName)
    val file = File(url.path)
    return file.readContent()

}


package com.igorvd.baseproject.data.utils

import timber.log.Timber
import java.io.*

/**
 * Reads the data for the given file
 */
fun File.readContent(): String {

    var ret = ""
    try {
        val inputStream = FileInputStream(this)

        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var receiveString = bufferedReader.readLine()

        while (receiveString != null) {
            ret += receiveString
            receiveString = bufferedReader.readLine()
        }
        inputStream.close()


    } catch (e: FileNotFoundException) {
        Timber.e(e, "file not found while reading file")
    } catch (e: IOException) {
        Timber.e(e, "IO Exception while reading file")
    }

    return ret
}
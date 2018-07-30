package com.igorvd.baseproject.domain.utils.extensions

import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

/**
 * @author Igor VIlela
 * @since 03/01/2018
 */


fun List<String>?.toStringWithSeparator(separator: String = ",") : String {

    if (this == null) return ""

    var valuesString = fold("", { total, value ->
        total + value + separator
    })

    valuesString.let {
        if(it.isNotEmpty()) {
            valuesString = it.dropLast(separator.length)
        }
    }

    return valuesString

}

fun String.containsDigit(): Boolean = this.filter { it.isDigit() }.isNotEmpty()

fun String.hasOnlyLetters(): Boolean = this.filter { it.isLetter() }.length == this.length
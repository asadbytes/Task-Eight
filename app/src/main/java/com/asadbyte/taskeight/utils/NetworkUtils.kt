package com.asadbyte.taskeight.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {

    fun getHttpURLConnection(urlStr: String, method: String): HttpURLConnection {
        val url = URL(urlStr)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = method
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Accept", "application/json")
        conn.doInput = true
        if (method in listOf("POST", "PUT", "DELETE")) {
            conn.doOutput = true
        }
        return conn
    }

    fun readResponse(conn: HttpURLConnection): String {
        val inputStream = if (conn.responseCode in 200..299) {
            conn.inputStream
        } else {
            conn.errorStream
        }

        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.use { it.readText() }
    }
}

package com.asadbyte.taskeight.apiService

import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse
import com.asadbyte.taskeight.utils.NetworkUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ManualApiService : ApiService {

    private val baseUrl = "https://httpbin.org"

    override suspend fun getUsers(): ApiResult<HttpBinResponse> = withContext(Dispatchers.IO) {
        val conn = NetworkUtils.getHttpURLConnection("$baseUrl/get", "GET")
        val responseText = NetworkUtils.readResponse(conn)
        val status = conn.responseCode
        conn.disconnect()

        val body = Gson().fromJson(responseText, HttpBinResponse::class.java)
        ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "Fetched data successfully" else "Failed to fetch data",
            body = body
        )
    }

    override suspend fun addUser(user: DummyUser): ApiResult<HttpBinResponse> = withContext(Dispatchers.IO) {
        val conn = NetworkUtils.getHttpURLConnection("$baseUrl/post", "POST")
        val json = Gson().toJson(user)
        conn.outputStream.use { it.write(json.toByteArray()) }

        val responseText = NetworkUtils.readResponse(conn)
        val status = conn.responseCode
        conn.disconnect()

        val body = Gson().fromJson(responseText, HttpBinResponse::class.java)
        ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User added successfully" else "Failed to add user",
            body = body
        )
    }

    override suspend fun updateUser(user: DummyUser): ApiResult<HttpBinResponse> = withContext(Dispatchers.IO){
        val conn = NetworkUtils.getHttpURLConnection("$baseUrl/put", "PUT")
        val json = Gson().toJson(user)
        conn.outputStream.use { it.write(json.toByteArray()) }

        val responseText = NetworkUtils.readResponse(conn)
        val status = conn.responseCode
        conn.disconnect()

        val body = Gson().fromJson(responseText, HttpBinResponse::class.java)
        ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User updated successfully" else "Failed to update user",
            body = body
        )
    }

    override suspend fun deleteUser(userId: Int): ApiResult<HttpBinResponse> = withContext(Dispatchers.IO){
        val conn = NetworkUtils.getHttpURLConnection("$baseUrl/delete", "DELETE")
        val json = JSONObject().apply {
            put("id", userId)
        }.toString()
        conn.outputStream.use { it.write(json.toByteArray()) }

        val responseText = NetworkUtils.readResponse(conn)
        val status = conn.responseCode
        conn.disconnect()

        val body = Gson().fromJson(responseText, HttpBinResponse::class.java)
        ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User deleted successfully" else "Failed to delete user",
            body = body
        )
    }
}

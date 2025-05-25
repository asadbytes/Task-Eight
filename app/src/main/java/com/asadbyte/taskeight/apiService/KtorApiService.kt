package com.asadbyte.taskeight.apiService

import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorApiService(private val client: HttpClient) : ApiService {
    private val baseUrl = "https://httpbin.org"
    override suspend fun getUsers(): ApiResult<HttpBinResponse> {
        val response = client.get("$baseUrl/get")
        val status = response.status.value
        val body = response.body<HttpBinResponse>()
        return ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "Fetched data successfully" else "Failed to fetch data",
            body = body
        )
    }

    override suspend fun addUser(user: DummyUser): ApiResult<HttpBinResponse> {
        val response = client.post("$baseUrl/post") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        val status = response.status.value
        val body = response.body<HttpBinResponse>()
        return ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User added successfully" else "Failed to add user",
            body = body
        )
    }

    override suspend fun updateUser(user: DummyUser): ApiResult<HttpBinResponse> {
        val response = client.put("$baseUrl/put") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        val status = response.status.value
        val body = response.body<HttpBinResponse>()
        return ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User updated successfully" else "Failed to update user",
            body = body
        )
    }

    override suspend fun deleteUser(userId: Int): ApiResult<HttpBinResponse> {
        val response = client.delete("$baseUrl/delete") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("id" to userId))
        }
        val status = response.status.value
        val body = response.body<HttpBinResponse>()
        return ApiResult(
            success = status in 200..299,
            statusCode = status,
            message = if (status in 200..299) "User deleted successfully" else "Failed to delete user",
            body = body
        )
    }
}

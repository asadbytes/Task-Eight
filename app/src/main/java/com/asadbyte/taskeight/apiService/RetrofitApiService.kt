package com.asadbyte.taskeight.apiService

import com.asadbyte.taskeight.network.RetrofitApi
import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse

class RetrofitApiService(private val api: RetrofitApi) : ApiService {

    override suspend fun getUsers(): ApiResult<HttpBinResponse> {
        val res = api.get()
        return ApiResult(
            success = res.isSuccessful,
            statusCode = res.code(),
            message = if (res.isSuccessful) "Fetched data successfully" else "Failed to fetch data",
            body = res.body()
        )
    }

    override suspend fun addUser(user: DummyUser): ApiResult<HttpBinResponse> {
        val res = api.post(user)
        return ApiResult(
            success = res.isSuccessful,
            statusCode = res.code(),
            message = if (res.isSuccessful) "User added successfully" else "Failed to add user",
            body = res.body()
        )
    }

    override suspend fun updateUser(user: DummyUser): ApiResult<HttpBinResponse> {
        val res = api.put(user)
        return ApiResult(
            success = res.isSuccessful,
            statusCode = res.code(),
            message = if (res.isSuccessful) "User updated successfully" else "Failed to update user",
            body = res.body()
        )
    }

    override suspend fun deleteUser(userId: Int): ApiResult<HttpBinResponse> {
        val res = api.delete(mapOf("id" to userId))
        return ApiResult(
            success = res.isSuccessful,
            statusCode = res.code(),
            message = if (res.isSuccessful) "User deleted successfully" else "Failed to delete user",
            body = res.body()
        )
    }
}

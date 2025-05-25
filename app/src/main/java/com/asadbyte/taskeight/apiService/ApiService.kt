package com.asadbyte.taskeight.apiService

import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse

interface ApiService {
    suspend fun getUsers(): ApiResult<HttpBinResponse>
    suspend fun addUser(user: DummyUser): ApiResult<HttpBinResponse>
    suspend fun updateUser(user: DummyUser): ApiResult<HttpBinResponse>
    suspend fun deleteUser(userId: Int): ApiResult<HttpBinResponse>
}
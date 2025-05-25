package com.asadbyte.taskeight.network

import com.asadbyte.taskeight.apiService.ApiService
import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse

class UserRepository(private val apiService: ApiService) {
    suspend fun get(): ApiResult<HttpBinResponse> = apiService.getUsers()
    suspend fun add(dummyUser: DummyUser): ApiResult<HttpBinResponse> = apiService.addUser(dummyUser)
    suspend fun update(dummyUser: DummyUser): ApiResult<HttpBinResponse> = apiService.updateUser(dummyUser)
    suspend fun delete(id: Int): ApiResult<HttpBinResponse> = apiService.deleteUser(id)
}
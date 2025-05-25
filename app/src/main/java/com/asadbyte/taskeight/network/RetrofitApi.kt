package com.asadbyte.taskeight.network

import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.model.HttpBinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/get")
    suspend fun get(): Response<HttpBinResponse>

    @POST("/post")
    suspend fun post(@Body user: DummyUser): Response<HttpBinResponse>

    @PUT("/put")
    suspend fun put(@Body user: DummyUser): Response<HttpBinResponse>

    @DELETE("/delete")
    suspend fun delete(@Query("id") body: Map<String, Int>): Response<HttpBinResponse>
}

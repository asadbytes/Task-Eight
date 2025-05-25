package com.asadbyte.taskeight.model

data class ApiResult<T>(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val body: T? = null
)

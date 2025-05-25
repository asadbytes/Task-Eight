package com.asadbyte.taskeight.model

data class HttpBinResponse(
    val args: Map<String, String>?,
    val headers: Map<String, String>?,
    val origin: String?,
    val url: String?,
    val json: DummyUser? = null
)

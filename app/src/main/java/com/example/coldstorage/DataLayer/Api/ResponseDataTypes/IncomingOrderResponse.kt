package com.example.coldstorage.DataLayer.Api.ResponseDataTypes

data class IncomingOrderResponse(
    val `data`: Data,
    val message: String,
    val status: String,
    val errorMessage :String?
)
package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class ResponseStockSummary(
    val status : String,
    val totalInitialQuantity: Int,
    val totalQuantityRemoved: Int,
    val message :String? = null
)



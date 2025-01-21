package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class Size(
    val currentQuantity: Int,
    val initialQuantity: Int,
    val quantityRemoved: Int,
    val size: String
)
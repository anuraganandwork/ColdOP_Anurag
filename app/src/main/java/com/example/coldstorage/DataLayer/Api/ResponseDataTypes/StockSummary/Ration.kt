package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class RationSummary(
    val currentQuantity: Int,
    val initialQuantity: Int,
    val quantityRemoved: Int
)
package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class GoliSummary(
    val currentQuantity: Int,
    val initialQuantity: Int,
    val quantityRemoved: Int
)
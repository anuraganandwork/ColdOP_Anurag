package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class Number12Summary(
    val currentQuantity: Int,
    val initialQuantity: Int,
    val quantityRemoved: Int
)
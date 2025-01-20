package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class CutTokSummary(
    val currentQuantity: Int,
    val initialQuantity: Int,
    val quantityRemoved: Int
)
package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StoreSummaryResponse

data class StockSummaryResponse(
    val status: String,
    val stockSummary: List<StoreStockItem>?
)

data class StoreStockItem(
    val variety: String,
    val sizes: List<StoreSize>
)

data class StoreSize(
    val size: String,
    val initialQuantity: Int,
    val currentQuantity: Int
)

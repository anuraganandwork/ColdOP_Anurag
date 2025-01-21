package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class StockSummaryDetailedResponse(
    val status: String,
    val stockSummary: List<StockSummary>
)
package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.StockSummary

data class PukhrajSummary(
    val Cuttok: CutTokSummary,
    val Goli: GoliSummary,
    val Number12: Number12Summary,
    val Ration: RationSummary,
    val Seed: SeedSummary
)
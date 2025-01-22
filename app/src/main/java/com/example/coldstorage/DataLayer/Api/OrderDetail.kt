package com.example.coldstorage.DataLayer.Api

data class OrderDetail(
    val bagSizes: List<BagSize>,
    val location: String,
    val variety: String
)
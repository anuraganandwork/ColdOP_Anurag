package com.example.coldstorage.DataLayer.Api

data class OrderDetail(
    val bagSizes: List<BagSize>,
    val location: Location,
    val variety: String
)
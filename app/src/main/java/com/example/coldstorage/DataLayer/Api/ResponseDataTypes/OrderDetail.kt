package com.example.coldstorage.DataLayer.Api.ResponseDataTypes

data class OrderDetail(
    val _id: String,
    val bagSizes: List<BagSize>,
    val location: Location,
    val variety: String
)
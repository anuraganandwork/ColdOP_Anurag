package com.example.coldstorage.DataLayer.Api.OutgoingData

data class OrderDddetailsOutgoing(
    val bagSizes: List<BagSize>,
    val variety: String
)
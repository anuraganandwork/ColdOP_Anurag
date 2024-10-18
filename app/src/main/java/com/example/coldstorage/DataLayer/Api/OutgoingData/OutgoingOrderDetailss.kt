package com.example.coldstorage.DataLayer.Api.OutgoingData

data class OutgoingOrderDetailss(
    val __v: Int,
    val _id: String,
    val coldStorageId: String,
    val createdAt: String,
    val dateOfExtraction: String,
    val farmerId: String,
    val orderDddetailsOutgoing: List<OrderDddetailsOutgoing>,
    val relatedOrders: List<String>,
    val updatedAt: String,
    val voucher: Voucher
)
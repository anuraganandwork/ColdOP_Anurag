package com.example.coldstorage.DataLayer.Api

data class IncomingOrderData(
    val coldStorageId: String,
    val dateOfSubmission: String,
    val farmerId: String,
    val orderDetails: List<OrderDetail>,
    val voucherNumber: Int
) 
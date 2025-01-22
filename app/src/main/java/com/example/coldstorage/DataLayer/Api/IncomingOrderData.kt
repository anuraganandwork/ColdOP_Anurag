package com.example.coldstorage.DataLayer.Api

data class IncomingOrderData(
    val coldStorageId: String,
    val dateOfSubmission: String,
    val farmerId: String,
    val remarks: String,
    val orderDetails: List<OrderDetail>,
    val voucherNumber: Int
) 
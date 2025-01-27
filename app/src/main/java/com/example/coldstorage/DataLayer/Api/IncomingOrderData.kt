package com.example.coldstorage.DataLayer.Api

data class IncomingOrderData(
    val coldStorageId: String,
    val dateOfSubmission: String,
    val farmerId: String,
    val remarks: String? = "Default Remarks",
    val orderDetails: List<OrderDetail>,
    val voucherNumber: Int
) 
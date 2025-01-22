package com.example.coldstorage.DataLayer.Api.ResponseDataTypes.GetAllOrderResponse

import java.time.LocalDate
import java.time.LocalDateTime

data class GetAllReciptResponse(
    val status: String,
    val data: List<Order>
)

data class Order(
    val voucher: Voucher,
    val _id: String,
    val coldStorageId: String,
    val farmerId: String,
    val dateOfSubmission: String,
    val fulfilled: Boolean,
    val orderDetails: List<OrderDetail>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class Voucher(
    val type: String,
    val voucherNumber: Int
)

data class OrderDetail(
    val location: String,
    val variety: String,
    val bagSizes: List<BagSize>
)

data class Location(
    val floor: String,
    val row: String,
    val chamber: String
)

data class BagSize(
    val quantity: Quantity,
    val size: String
)

data class Quantity(
    val initialQuantity: Int,
    val currentQuantity: Int
)
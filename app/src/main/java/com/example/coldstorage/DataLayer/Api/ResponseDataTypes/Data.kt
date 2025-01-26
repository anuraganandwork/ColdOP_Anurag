package com.example.coldstorage.DataLayer.Api.ResponseDataTypes

data class Data(
    val __v: Int,
    val _id: String,
    val coldStorageId: String,
    val createdAt: String,
    val dateOfSubmission: String,
    val farmerId: String,
    val fulfilled: Boolean,
    val remarks:String,
    val orderDetails: List<OrderDetail>,
    val updatedAt: String,
    val voucher: Voucher
)
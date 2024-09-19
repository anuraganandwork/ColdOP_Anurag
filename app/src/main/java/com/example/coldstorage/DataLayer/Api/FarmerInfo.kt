package com.example.coldstorage.DataLayer.Api

data class FarmerInfo(
    val _id: String,
    val address: String,
    val farmerId: String,
    val imageUrl: String,
    val isVerified: Boolean,
    val mobileNumber: String,
    val name: String,
    val role: String
)
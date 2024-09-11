package com.example.coldstorage.DataLayer.Api

data class StoreAdminData(
    val _id: String,
    val coldStorageDetails: ColdStorageDetails,
    val imageUrl: String,
    val isActive: Boolean,
    val isPaid: Boolean,
    val isVerified: Boolean,
    val mobileNumber: String,
    val name: String,
    val personalAddress: String,
    val role: String,
    val storeAdminId: Int
)
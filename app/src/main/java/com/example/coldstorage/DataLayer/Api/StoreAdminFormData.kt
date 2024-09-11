package com.example.coldstorage.DataLayer.Api

data class StoreAdminFormData(
    val coldStorageAddress: String,
    val coldStorageContactNumber: String,
    val coldStorageName: String,
    val imageUrl: String,
    val isVerified: Boolean,
    val mobileNumber: String,
    val name: String,
    val password: String,
    val personalAddress: String
)
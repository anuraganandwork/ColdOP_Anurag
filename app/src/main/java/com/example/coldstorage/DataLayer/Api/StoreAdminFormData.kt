package com.example.coldstorage.DataLayer.Api

data class StoreAdminFormData(
    val name: String,
    val personalAddress: String,
    val mobileNumber: String,
    val coldStorageName: String,
    val coldStorageAddress: String,
    val coldStorageContactNumber: String,
    val password: String,
    val imageUrl: String,
    val isVerified: Boolean,
    val isMobile: Boolean = true
)
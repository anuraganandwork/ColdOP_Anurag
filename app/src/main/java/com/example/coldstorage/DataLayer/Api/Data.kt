package com.example.coldstorage.DataLayer.Api

import android.os.Message

data class DataStoreOwner(
    val _id: String,
    val coldStorageDetails: ColdStorageDetailsX,
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

// Main response data class
data class StoreAdminResponse(
    val status: String,   // "Success"
    val data: StoreAdminDetails,
    val message: String?// Nested object representing the "data" field
)

// Data class representing the "data" field
data class StoreAdminDetails(
    val name: String,              // "Gurraj Singh"
    val personalAddress: String,   // "108 new jawahar nagar"
    val mobileNumber: String,      // "9915487926"
    val coldStorageInfo: ColdStorageInfo, // Nested cold storage details
    val isVerified: Boolean,       // true
    val isActive: Boolean,         // false
    val isPaid: Boolean,           // false
    val role: String,
    val token:String,// "store-admin"
    val storeAdminId: Int,         // 2
    val imageUrl: String,          // ""
    val _id: String                 // "66e1f22d782bbd67d3446805"
)

// Data class representing the "coldStorageDetails" field
data class ColdStorageInfo(
    val storageName: String,           // "Singh cold storage"
    val storageAddress: String,        // "abc 123 , jalandhar"
    val contactNumber: String          // "9915487926"
)

package com.example.coldstorage.DataLayer.Api

data class StoreAdminModel(
    val `data`: StoreAdminData,
    val success: Boolean
)


data class sendOtpResponse(
    val status: String,
    val message: String
)

data class verifyMobile(
   val mobileNum:String,
    val otp:String
)
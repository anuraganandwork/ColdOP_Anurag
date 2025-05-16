package com.example.coldstorage.DataLayer.Api

data class StoreAdminModel(
    val `data`: StoreAdminData,
    val success: Boolean
)


data class sendOtpResponse(
    val status: String,
    val message: String
)

data class verifyMobiledata(
   val mobileNumber:String,
    val enteredOtp:String
)

data class sendOtpReq(
    val mobileNumber :String
)
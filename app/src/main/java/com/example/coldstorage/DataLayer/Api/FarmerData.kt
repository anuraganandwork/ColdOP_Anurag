package com.example.coldstorage.DataLayer.Api

data class FarmerDataSave(
    val name: String,
    val farmerId :Int,
    val address: String,
    val mobileNumber: String,
    val password: String,
    val imageUrl: String,

    )

data class logInData(
    val mobileNumber: String,
    val password: String,
    val isMobile:Boolean
)
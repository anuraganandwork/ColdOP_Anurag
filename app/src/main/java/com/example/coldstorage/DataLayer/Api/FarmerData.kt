package com.example.coldstorage.DataLayer.Api

data class FarmerData(
    val name: String,
    val accNum :String,
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